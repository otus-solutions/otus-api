###############################################
###               Variables                 ###
###############################################

variable "otus-api-name" {
  default = "otus-api"
}
variable "otus-api-directory" {
  default = "otus-api"
}
variable "otus-api-source" {
  default = "source"
}

variable "otus-api-mvnbuild" {
  default = "clean install"
}

variable "otus-database-name" {
  default = "otus-database"
}

variable "otus-database-directory" {
  default = "otus-api"
}

variable "otus-database-source" {
  default = "source"
}


###############################################
###  OTUS-API : Build Image Service         ###
###############################################
resource "null_resource" "mvn-build" {
  provisioner "local-exec"{
   command = "mvn ${var.otus-api-mvnbuild} -f ${var.otus-api-source}/otus-root"
  }
}
resource "null_resource" "otus-api" {
  depends_on = [null_resource.mvn-build]

  provisioner "local-exec" {
    command = "docker build --target api -t ${var.otus-api-name} ."
  }
}

###############################################
###  OTUS-DATABASE : Build Image Database   ###
###############################################
resource "null_resource" "otus-database" {
  provisioner "local-exec" {
    command = "docker build --target database -t ${var.otus-database-name} ."
  }
}