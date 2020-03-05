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

variable "otus-api-jvm-memory" {
  default = "3g"
}

variable "otus-api-version" {
	default = "latest"
}

resource "docker_image" "otus-api" {
  name = "otus-api:${var.otus-api-version}"
}


variable "otus-api-network" {
  default = "otus-api-network"
}

resource "docker_container" "otus-api" {
  name = "otus-api"
  image = "${docker_image.otus-api.name}"
  env = [
        "DEBUG_MODE=${var.otus-api-debug}",
        "JVM_MEMORY=${var.otus-api-jvm-memory}"
  ]
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
    name    = "${var.otus-api-network}"
  }
}
