pipeline {
  agent any
  triggers {
    pollSCM('*/1 * * * *')
  }
  tools {
    maven 'mvn3'
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '3'))
  }

  stages {
    stage('Unit test') {
      steps {
        sh 'cd samples; mvn test'
      }
    }
  }
  post {
    always {
      junit '**/target/surefire-reports/*.xml'
    }
  }
}