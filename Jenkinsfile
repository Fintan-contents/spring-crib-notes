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
          dockerfile {
            filename 'Dockerfile.build'
          }
      }
      steps {
        sh 'cd doc; export LINKCHECK=true; make clean linkcheck'
        sh 'cd doc; unset LINKCHECK; make clean html'
      }
      post {
        always {
          archiveArtifacts 'doc/_build/**'
          step([
              $class: 'WarningsPublisher',
              consoleParsers: [
                  [parserName: 'Sphinx-build'],
              ],
              canComputeNew: false,
              unstableTotalAll: '0'
          ])
        }
        failure {
          slackSend message: "sphinx failed :angry: - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", color: 'danger'
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
        success {
          slackSend message: ":grinning: - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", color: 'good'
        }
        failure {
          slackSend message: ":angry: - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", color: 'danger'
        }
        unstable {
          slackSend message: ":thinking_face: - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", color: 'warning'
        }
      }
    }
    stage('alfort push') {
      when {
        expression {
          ['master', 'develop'].contains(env.BRANCH_NAME)
        }
      }
      steps {
        withCredentials(
            [usernamePassword(
                credentialsId: 'c01cba08-cf94-4a91-9f83-c3579b277c00',
                passwordVariable: 'GIT_PASSWORD',
                usernameVariable: 'GIT_USERNAME')]) {
          sh('git branch temp-branch')
          sh('git push https://${GIT_USERNAME}:${GIT_PASSWORD}@alfort.adc-tis.com/gitbucket/git/keel/spring-crib-notes.git temp-branch:${BRANCH_NAME}')
        }
      }
      post {
        failure {
          slackSend message: "push to alfort failed :angry: - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", color: 'danger'
        }
      }
    }
  }
}