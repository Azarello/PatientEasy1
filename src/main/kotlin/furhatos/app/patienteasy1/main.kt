package furhatos.app.patienteasy1

import furhatos.app.patienteasy1.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class Patienteasy1Skill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
