###############################################
###               Variables                 ###
###############################################

variable "otus-api" {
  type = "map"
  default = {
    "name" = "otus-api"
    "directory" = "otus-api"
    "source" = "source/otus-root"
    "mavenBuildCommand" = "clean install"
  }
}
variable "otus-database" {
  type = "map"
  default = {
    "name" = "otus-database"
    "directory" = "otus-api"
    "source" = "/source"
  }
}

###############################################
###  OTUS-API : Build Image Service         ###
###############################################
resource "null_resource" "otus-api" {
  provisioner "local-exec"{
   command = "mvn ${var.otus-api["mavenBuildCommand"]} -f ${var.otus-api["source"]}/pom.xml"
  }
  provisioner "local-exec"{
   command = "docker build --target api -t ${var.otus-api["name"]} ."
  }
}

###############################################
###  OTUS-DATABASE : Build Image Database   ###
###############################################
resource "null_resource" "otus-database" {
  provisioner "local-exec" {
    command = "docker build --target database -t ${var.otus-database["name"]} ."
  }
}