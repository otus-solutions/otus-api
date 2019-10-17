###############################################
###               Variables                 ###
###############################################

variable "otus-api" {
  type = "map"
  default = {
    "name" = "otus-api"
    "directory" = "otus-api"
    "source" = "source/otus-root"
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
   command = "cd ${var.otus-api["directory"]}/${var.otus-api["source"]} && mvn clean install -DskipTests && cd ../../../ && docker build -t ${var.otus-api["name"]} ${var.otus-api["directory"]}" 
  }
}

###############################################
###  OTUS-DATABASE : Build Image Database   ###
###############################################
resource "null_resource" "otus-database" {
  provisioner "local-exec" {
    command = "docker build --target database -t ${var.otus-database["name"]} ${var.otus-database["directory"]}"
  }
}