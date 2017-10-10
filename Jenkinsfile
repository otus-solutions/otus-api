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
            always {
              //archive '**/target/surefire-reports/*'
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
    post {
      failure {
        emailext (
   subject: "Deploy failed in Jenkins '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
   body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
     <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>""",
   recipientProviders: [[$class: 'DevelopersRecipientProvider']]
 )
          }
    }
  }
    }

}
