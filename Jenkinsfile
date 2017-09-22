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
	    
	stage('Build - Development Server') {
		steps {
                	sh 'mvn -f otus-root/pom.xml clean install -Ddatabase.host=api-otus.dev.ccem.ufrgs.br -Ddatabase.username=otus -Ddatabase.password=LeBxL3lnhDAyvF' 
            	}    			
    	}
	    
	stage('Deploy - Development Server') {
		steps {
                	sh 'mvn -f otus-ear/pom.xml wildfly:deploy -Dwildfly-hostname=api-otus.dev.ccem.ufrgs.br -Dwildfly-username=admin -Dwildfly-password=TFoZD4j9YFsatm' 
            	}    			
    	}
    }
}
