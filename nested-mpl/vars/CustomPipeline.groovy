def call(body){
    MPLInit()
    def MPL = MPLPipelineConfig(body, [
    agent_label: '',
    modules: [
      Checkout: [:],
      BuildAndDeploy: [:],
      Test: [:]
    ]
  ])

  pipeline {
    agent {
      label MPL.agentLabel
    }
    options {
      skipDefaultCheckout(true)
    }
    stages {
      stage( 'Checkout' ) {
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }
      stage( 'BuildAndDeploy' ) {
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }
      stage( 'Test' ) {
        when { expression { MPLModuleEnabled() } }
        steps {
          MPLModule()
        }
      }
    }
    post {
      always {
        MPLPostStepsRun('always')
      }
      success {
        MPLPostStepsRun('success')
      }
      failure {
        MPLPostStepsRun('failure')
      }
    }
  }
}