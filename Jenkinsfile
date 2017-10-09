pipeline {
    agent any
    tools {
        maven 'maven 3.5.0'
        jdk 'Java8'
    }
    node {
  sshagent (credentials: ['deploy-dev']) {

    sh 'ssh -L 9990:localhost:9990 ${SERVER_HOST}'
  }
}

    stages {
    	stage('Build Aplication') {
		      steps {
            sh 'mvn -f otus-root/pom.xml clean install'
          }
          post {
            always {
              junit '**/target/surefire-reports/*.xml'
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
			sh 'mvn -f otus-root/pom.xml sonar:sonar -Dsonar.host.url=${URL_SONAR} -Dsonar.password=${PWD_SONAR} -Dsonar.login=${USER_SONAR}'
            	}
    	}

	stage('Build - Development Server') {
		steps {
			sh 'mvn -f otus-root/pom.xml clean install -Ddatabase.host=${DATABASE_DEV_HOST} -Ddatabase.username=${DATABASE_USER} -Ddatabase.password=${DATABASE_PWD}'
            	}

    	}

	stage('Deploy - Development Server') {
		steps {
			sh 'mvn -f otus-ear/pom.xml wildfly:deploy -Dwildfly-hostname=${SERVER_HOST} -Dwildfly-username=${SERVER_USER} -Dwildfly-password=${SERVER_PWD}'
            	}
    	}
    }

}
