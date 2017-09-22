pipeline {
    agent any
    tools {
        maven 'maven 3.5.0'
        jdk 'java8'
    }


    stages {
    	stage('Build Aplication') {
		steps {
                	sh 'mvn -f otus-root/pom.xml clean install' 
            	}
        	post {
                	success {
                    		junit 'target/surefire-reports/**/*.xml' 
                	}
            	}
    	}
    }
}
