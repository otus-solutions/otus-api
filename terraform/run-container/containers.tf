variable "otus-api" {
  type = "map"
  default = {
	"name" = "otus-api"
	"persistence-directory" = "/home/drferreira/otus-platform/docker-persistence/otus-api"
	"port_http" = 51002
	"port_management" = 51007
  }
}

variable "otus-database" {
  type = "map"
  default = {
	"name" = "otus-database"
	"persistence-directory" = "/home/drferreira/otus-platform/docker-persistence/otus-database"
	"port" = 51003}
}

resource "docker_image" "otus-api" {
  name = "otus-api:latest"
}

resource "docker_image" "otus-database" {
  name = "otus-database:latest"
}

resource "docker_network" "otus-api-network" {
  name = "otus-api-network"
}

resource "docker_container" "otus-api" {
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

resource "docker_container" "otus-database" {
  name = "otus-database"
  image = "${docker_image.otus-database.latest}"
  sysctls = "--wiredTigerCacheSizeGB=2"
  ports {
	internal = 27017
	external = "${var.otus-database["port"]}"
  }
  volumes {
	host_path = "${var.otus-database["persistence-directory"]}"
	container_path = "/data/db"
  }
  networks_advanced {
    name    = "${docker_network.otus-api-network.name}"
  }
  
}
