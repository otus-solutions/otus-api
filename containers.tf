variable "otus-api" {
  type = "map"
  default = {
	"name" = "otus-api"
	"persistence-directory" = "/home/drferreira/otus-platform/docker-persistence/otus-api"
	"port_http" = 51002
	"port_management" = 51007
  }
}

variable "otus-api-database" {
  type = "map"
  default = {
	"name" = "otus-api-database"
	"persistence-directory" = "/home/drferreira/otus-platform/docker-persistence/otus-api-database"
	"port" = 51003
  }
}

resource "docker_image" "otus-api" {
  name = "otus-api:latest"
}

resource "docker_image" "otus-api-database" {
  name = "otus-api-database:latest"
}

resource "docker_network" "otus-api-network"{
  name = "otus-api-network"
}

resource "docker_container" "otus-api" {
  depends_on = [docker_container.otus-api-database]
  name = "otus-api"
  image = "${docker_image.otus-api.latest}"
  ports {
	internal = 8080
	external = "${var.otus-api["port_http"]}"
  }
  ports {
	internal = 9990
	external = "${var.otus-api["port_management"]}"
  }
  networks_advanced {
    name    = "${docker_network.otus-api-network.name}"
  }
}

resource "docker_container" "otus-api-database" {
  name = "otus-api-database"
  image = "${docker_image.otus-api-database.latest}"
  ports {
	internal = 27017
	external = "${var.otus-api-database["port"]}"
  }
  volumes {
	host_path = "${var.otus-api-database["persistence-directory"]}"
	container_path = "/data/db"
  }
  networks_advanced {
    name    = "${docker_network.otus-api-network.name}"
  }
}
