variable "otus-database-persistence"{
  default = "/otus-platform/docker-persistence/otus-database"
}

variable "otus-database-port" {
	default = 51003
}

variable "otus-database-version" {
	default = "latest"
}

variable "otus-api-persistence"{
  default = "/otus-platform/docker-persistence/otus-api"
}

variable "otus-api-porthttp"{
  default = 51002
}

variable "otus-api-portmanagement"{
  default = 51007
}
variable "otus-api-debug-port"{
  default = 8787
}
variable "otus-api-debug"{
  default = false
}
variable "otus-api-version" {
	default = "latest"
}
resource "docker_image" "otus-api" {
  name = "otus-api:${var.otus-api-version}"
}

resource "docker_image" "otus-database" {
  name = "otus-database:${var.otus-database-version}"
}

resource "docker_network" "otus-api-network" {
  name = "otus-api-network"
}

resource "docker_container" "otus-api" {
  name = "otus-api"
  image = "${docker_image.otus-api.name}"
  env = ["DEBUG_MODE=${var.otus-api-debug}"]
  ports {
	  internal = 8080
	  external = "${var.otus-api-porthttp}"
  }
  ports {
	  internal = 9990
	  external = "${var.otus-api-portmanagement}"
  }
  ports {
	  internal = 8787
	  external = "${var.otus-api-debug-port}"
  }
  networks_advanced {
    name    = "${docker_network.otus-api-network.name}"
  }
}

resource "docker_container" "otus-database" {
  name = "otus-database"
  image = "${docker_image.otus-database.name}"
  ports {
	internal = 27017
	external = "${var.otus-database-port}"
  }
  volumes {
	host_path = "${var.otus-database-persistence}"
	container_path = "/data/db"
  }
  networks_advanced {
    name    = "${docker_network.otus-api-network.name}"
  }
}
