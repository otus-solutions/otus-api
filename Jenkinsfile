pipeline {
    agent any
    tools {
        maven 'maven 3.5.0'
        jdk 'Java8'
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
	    
	stage('Publish artifact') {
		steps {
                	sh 'mvn -f otus-root/pom.xml deploy' 
            	}        	
    	}
	    
	stage('Sonar Update') {
		steps {
                	sh 'mvn -f otus-root/pom.xml sonar:sonar -Dsonar.host.url=http://35.193.3.148 -Dsonar.password=f8Gov4WljZkJQv -Dsonar.login=jenkins' 
            	}        	
    	}
    }
}
