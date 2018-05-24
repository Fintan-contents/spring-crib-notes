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
    stage('Sphinx') {
      agent {
          docker {
            image 'higebu/sphinx-latexpdf'
          }
      }
      steps {
        sh 'cd doc; make html'
      }
      post {
        always {
          archiveArtifacts 'doc/_build/**'
        }
      }
    }
    stage('Unit test') {
      steps {
        sh 'cd samples; mvn test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }
  }
}