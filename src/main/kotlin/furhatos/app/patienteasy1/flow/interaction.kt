package furhatos.app.patienteasy1.flow

import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.app.patienteasy1.nlu.*
import org.apache.logging.log4j.core.async.JCToolsBlockingQueueFactory
import org.netlib.util.Second
import java.util.*

val Start : State = state(Interaction) {

    onEntry {

        furhat.ask("Hello and welcome to this program, in which you will learn more about Intensive Short-Term " +
                "Dynamic Psychotherapy. The first step in the therapeutic process, is to establish a conscious " +
                "therapeutic alliance. The following program will guide you through different modules that train " +
                "the skills necessary in establishing this alliance. When you are ready to start the first module, " +
                " say start.")

    }

    onResponse<Continue>{
        goto(FirstModule)
    }

    onResponse<No> {
        furhat.say("Ok, restart me when you are ready to begin")
        goto(Idle)
    }
}

val FirstModule1 : State = state {
    onEntry {
        goto(FirstModule)
    }
}

val FirstModule : State = state {
    onEntry {
        furhat.say("The first step in establishing a conscious therapeutic alliance, is to get the patient to " +
                "declare an internal emotional problem, meaning they explicitly state an issue they are struggling" +
                " with. It is important for both patient and therapist to have a clear " +
                " picture of the issue to start therapy. Patients are often reluctant to explicitly state their " +
                "issue immediately, and use various defense mechanisms to avoid talking about the problem. ")
        furhat.say("The defense mechanisms can be divided into different categories depending on their structure. " +
                " In this module you will learn to" +
                " identify different kinds of defenses, which is the first step in learning to deal with them appropriately.  " +
                "The module is complete once you have correctly identified five defenses, resulting in the " +
                "patient clearly stating their problem. ")
        furhat.ask("Say continue if you are ready to start, or say repeat if you would like to hear the " +
                "instructions again")
    }



    onResponse<Continue> {
        furhat.say("Ok, this will be the first defense.")
        goto(DeclareProblem)
    }

    onResponse<Repeat> {
        goto(FirstModule1)
    }
}

val DeclareWait : State = state {
    onTime(delay=1500) {
        goto(DeclareProblem)
    }
}

val DeclareProblem : State = state {

    val rand = Random()
    val num = rand.nextInt(5)
    onEntry {
        when (num) {
            0 -> goto(Vague1)
            1 -> goto(Denial1)
            2 -> goto(Projection1)
            3 -> goto(CoverWord1)
            4 -> goto(Rationalization1)
        }
    }
}

var counter1 = 0

val Counter1 : State = state {



    onEntry {
        counter1 += 1
        if (counter1 < 5) {
            furhat.say(" Let's move on to the next defense")
            goto(DeclareProblem) }
        else {
            furhat.say("My problem is that I get very angry with my father sometimes when we speak")
            goto(Resolution1)

        }
    }
}
val Wait1 : State = state {

    onTime(delay=1500) {
        furhat.ask("When you are ready for the next defense, say next")
    }

    onResponse<Continue> {
        goto(Counter1)
    }
}

val Vague1Goto : State = state {
    onEntry {
        goto(Vague1)
    }
}

val Vague1 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)
    onEntry {
        when (num) {
            0 -> furhat.ask("I've just been feeling kind of down lately and not sure what is the matter")
            1 -> furhat.ask("I've been having these episodes for a while that are sort of a problem")
            2 -> furhat.ask("It's something to do with my father, just stuff going on that's complicated")
            3 -> furhat.ask("It's just these things happening in my life you know")
            4 -> furhat.ask("Some issues have been coming up that I'm not sure about")
        }
    }

    onResponse<VagueBlock1> {

        it.intent.specific
        it.intent.avoid
        it.intent.notice
        it.intent.negative
        it.intent.person
        it.intent.problem
        it.intent.feel

        when (num) {
            0 -> furhat.say("Correct. Vague answers prevent the patient and therapist from getting a clear picture " +
                    "of the problem, inhibiting the therapeutic process.")
            1 -> furhat.say("Great. A good way of blocking vagueness is by explaining to the patient " +
                    "how they are avoiding the issue and encouraging them to be more specific.")
            2 -> furhat.say("Yes. Vague defenses can often be identified because the patient does not provide any " +
                    "specific details about their problem. They paint with a wide brush without providing " +
                    "much information.")
            3 -> furhat.say("Exactly right. Expressions like “something” or “stuff going on” are indicators that " +
                    "the patient is being vague about expressing their problem.")
            4 -> furhat.say("Good job. Instead of settling for the vague statement “some issues have been coming up”, " +
                    "it is helpful to block the vagueness by asking the patient to be more specific about this.")

        }

        goto(Wait1)
    }

    onResponse<Hint> {
        furhat.say("Note that the patient is not giving you very specific information about their problem. " +
                " It is difficult to work with a problem that is not clearly expressed.")
        furhat.say("The following defense will be of the same type, see if you can spot a similarity.")
        goto(Vague1Goto)
    }

    onResponse<TryAgain> {
        furhat.say("Let's try another defense")
        goto(DeclareProblem)
    }

    onResponse<GiveAnswer> {
        furhat.say("It was a vague defense. They can often be identified because they don't give a clear or" +
                " specific explanation of a problem, but rather ambiguous and unclear references.  ")
        furhat.say(" Let's go to another defense")
        goto(DeclareWait)
    }

    onResponse<DenialBlock1> {
        furhat.say(" Good guess. Though the patient is in a way denying you access to their emotions, denial implies" +
                " a more direct opposition to sharing their feelings. The next defense will be of the same category")
        goto(Vague1Goto)
    }

    onResponse<ProjectionBlock1> {
        furhat.say(" Not quite. Keep in mind that just because a patient mentions another person it does not necessarily " +
                " mean they are projecting. There may be other more distinct defense mechanisms at play. See if you can" +
                " spot it in the next defense of the same category.")
        goto(Vague1Goto)
    }

    onResponse<CoverWordBlock1> {
        furhat.say(" Not so. Consider what word you thought was a cover. Generally cover words stick out from the context" +
                " or when a patient is clearly more emotional than the words they use.")
        furhat.say(" Try another defense from the same category")
        goto(Vague1Goto)
    }

    onResponse<RationalizationBlock1> {
        furhat.say(" Not really. In this case the patient is not providing reasons for what they are feeling," +
                " which is the basis of rationalization. See if you can see the principle at play in the next defense " +
                " of the same kind")
        goto(Vague1Goto)
    }

}



val Denial1Goto : State = state {
    onEntry {
        goto(Denial1)
    }

}

val Denial1 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)
    onEntry {
        when (num) {
            0 -> furhat.ask(" Actually I don't have a problem that merits so much attention, not sure what I'm doing here")
            1 -> furhat.ask(" It's really not a big deal, I'm not bothered by it most of the time")
            2 -> furhat.ask(" I'm not really feeling all that bad when I think about it, it's just a minor thing")
            3 -> furhat.ask(" It seemed a lot worse before, now it doesn't strike me as very troubling or important")
            4 -> furhat.ask(" I've just been a little off, but it's not really a major issue")
        }
    }

    onResponse<DenialBlock1> {

        it.intent.deny
        it.intent.feel
        it.intent.notice
        it.intent.specific
        it.intent.problem
        it.intent.avoid

        when (num) {
            0 -> furhat.say("Yes. There are different kinds of denials, which all have to do with the " +
                    "patient in one way or another denying the reality of their emotional problem.")
            1 -> furhat.say(" Correct.One way of dealing with denial defenses is by pointing out to the " +
                    "patient that after all they came to a therapist so there must be something going on.")
            2 -> furhat.say("Exactly right. In this case it is helpful to focus in on the “minor thing” to " +
                    "see if they are denying its importance and significance in their life.")
            3 -> furhat.say("Good job. Denial can take the shape of a patient denying the stimulus of a problem, " +
                    "such as a conflict with a parent, or by denying the emotional distress itself.")
            4 -> furhat.say("Excellent. This kind of denial is often referred to as minimization, in which the " +
                    "patient acknowledges they have a problem but downplays its significance and impact.")
        }


        goto(Wait1)
    }

    onResponse<Hint> {
        furhat.say("What do you notice about the patients relationship to their issue? Are they able to accept" +
                " that they have a significant problem in the first place?")
        furhat.say("Keep these questions in mind when listening to the next defense of the same kind ")
        goto(Denial1Goto)
    }

    onResponse<TryAgain> {
        furhat.say("Let's try again with another defense")
        goto(DeclareProblem)
    }

    onResponse<GiveAnswer> {
        furhat.say("That was a denial. You can often identify it because the patient refuses to talk about their " +
                " problem. Either they don't want to mention what is causing it, or the troubling emotion itself. ")
        furhat.say( "Now let's go to the next defense")
        goto(DeclareWait)
    }

    onResponse<VagueBlock1> {
        furhat.say(" Good guess, the patient is indeed not specific about their issue. However, there is another  " +
                " way they are avoiding talking about the problem. See if you can catch it in the following similar defense")
        goto(Denial1Goto)
    }

    onResponse<ProjectionBlock1> {
        furhat.say(" Not really. The patient is not referencing others in their defense so it's not a question of" +
                " projection.")
        goto(Denial1Goto)
    }

    onResponse<CoverWordBlock1> {
        furhat.say(" Nice try. The patient does use rather weak language and downplays the importance of their emotions. " +
                " See if you can identify another defense mechanism that fits even better for this particular scenario")
        goto(Denial1Goto)
    }

    onResponse<RationalizationBlock1> {
        furhat.say(" Not bad. It may seem like the patient is rationalizing because they are dancing around the issue " +
                " a bit. However, rationalization is when the patient provides reasons for their emotions rather than " +
                " the feelings themselves, which the patient is not doing here. Try again with the next similar defense")
        goto(Denial1Goto)
    }
}


val Projection1Goto : State = state {
    onEntry {
        goto(Projection1)
    }
}

val Projection1 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)
    onEntry {
        when (num) {
            0 -> furhat.ask(" Well I don't think it's a problem, my wife made me come here")
            1 -> furhat.ask(" Actually it was my doctor's idea for me to come here after my medication didn't help")
            2 -> furhat.ask(" You sure seem to think I have a problem that needs attention")
            3 -> furhat.ask(" My son thought I should see someone so I'm really doing it for his sake")
            4 ->furhat.ask("If my dad weren't annoying I wouldn't need to come here, it's really him with the issues")
        }
    }

    onResponse<ProjectionBlock1> {

        it.intent.person
        it.intent.problem
        it.intent.avoid
        it.intent.will
        it.intent.force
        it.intent.intellect
        it.intent.obey
        it.intent.specify
        it.intent.feel
        it.intent.notice

        when (num) {
            0 -> furhat.say("Excellent. In projection a patient may blame others as the cause of their " +
                    "problem, or ignore their issue by claiming that only other people believe there is a problem.")
            1 -> furhat.say("Perfect. As with other defenses, one way of blocking projection is to simply point out the " +
                    "defense explicitly to the patient. Ask them how they think without reference to others.")
            2 -> furhat.say(" Yes that's projection. As is shown in this example, it is not uncommon for patients " +
                    "to project on to the therapist. The patient does not think he has a problem, only the therapist does.")
            3 -> furhat.say("Good job. Projection can often be identified when patients make reference to other people " +
                    "without going into their own emotional state")
            4 -> furhat.say(" Exactly right. This example shows an example of how the patient projects his problem " +
                    "as in fact being his fathers issue. ")
        }

        goto(Wait1)
    }

    onResponse<Hint> {
        furhat.say(" Notice whether the patient is taking responsibility for their issue. Are there other people" +
                "involved in the patient's description?")
        furhat.say("See in the next defense of the same type you can catch in what way the patient is avoiding " +
                "their own problem")
        goto(Projection1Goto)
    }

    onResponse<TryAgain> {
        furhat.say("No problem. Let's try another defense instead.")
        goto(DeclareProblem)
    }

    onResponse<GiveAnswer> {
        furhat.say("That was a projection defense. Whenever the patient mentions other people and not their own feelings" +
                " it is a good sign that they may be projecting.")
        furhat.say(" Let's try another defense. ")
        goto(DeclareWait)
    }

    onResponse<VagueBlock1> {
        furhat.say(" Not quite. The patient is actually being rather clear with what they think in this case. In " +
                " the next defense, listen for the underlying reason the patient is not providing more specific information")
        goto(Projection1Goto)
    }

    onResponse<DenialBlock1> {
        furhat.say(" Good guess. It is true the patient is denying having a problem, but what is critical is the way" +
                " in which they are rejecting their issue. See if you can catch it in the next defense of a similar kind.")
        goto(Projection1Goto)
    }

    onResponse<CoverWordBlock1> {
        furhat.say(" Not really. Give it another try in the following defense of the same kind.")
        goto(Projection1Goto)
    }

    onResponse<RationalizationBlock1> {
        furhat.say(" This is a very good guess and correct in a sense. The patient is indeed providing reasons " +
                "instead of feelings. Dig deeper into what reasons they are describing and see if you spot another principle" +
                " at play in the following defense ")
        goto(Projection1Goto)
    }



}

val CoverWord1Goto : State = state {
    onEntry {
        goto(CoverWord1)
    }
}

val CoverWord1 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)
    onEntry {
        when (num) {
            0 -> furhat.ask("I've been feeling a bit infuriated about my dad")
            1 -> furhat.ask(" It's just kind of annoying when he does the same thing all the time")
            2 -> furhat.ask(" My dad has this way of making me slightly upset when we talk")
            3 -> furhat.ask(" There are these situations when I feel rather disconcerted")
            4 -> furhat.ask(" He has a way of talking that's just rather bothersome and gets to me")
        }
    }

    onResponse<CoverWordBlock1> {

        it.intent.person
        it.intent.cover
        it.intent.negative
        it.intent.feel
        it.intent.notice
        it.intent.specify

        when (num) {
            0 -> furhat.say(" Yes this is a cover word defense. Cover words are words of weaker emotional " +
                    "import than the patient is actually feeling.")
            1 -> furhat.say(" Correct. 'Kind of annoying' is a cover word in this case. Clearly the patient " +
                    "must be feeling something stronger since they are unlikely to seek therapy for slight annoyance.")
            2 -> furhat.say(" Cover word is exactly right. Blocking cover words can be done by illuminating the " +
                    "particular word to the patient and inviting them to share more of their emotions")
            3 -> furhat.say(" Good job, cover words can often be identified because they don't give weight enough to the " +
                    "situation or seem contrived. The word disconcerted used in this example really sticks out.")
            4 -> furhat.say(" Like other defenses, cover words are used to distance the patient from their " +
                    "actual painful emotions. By inviting them to use more appropriate words they may feel more " +
                    "comfortable exploring their feelings and vice versa.")
        }
        goto(Wait1)
    }

    onResponse<Hint> {
        furhat.say("Pay attention to the terminology of the patient. Does he use phrases that are a bit unusual " +
                "or somehow seem out of place?")
        furhat.say("A similar kind of defense will follow soon, see if you can see the pattern")
        goto(CoverWord1Goto)
    }


    onResponse<TryAgain> {
        furhat.say("Ok, this one was pretty hard. I will give you another defense for now.")
        goto(DeclareProblem)
    }

    onResponse<GiveAnswer> {
        furhat.say("That was a cover word block. When identifying this defense, look for words that express weak" +
                " emotional content. Oftentimes patients may use unusual adjectives as covers so that's a good hint.")
        furhat.say(" Prepare for the next defense coming up.")
        goto(DeclareWait)
    }

    onResponse<VagueBlock1> {
        furhat.say(" Good guess, the patient is indeed vague about their issue. However they are avoiding their " +
                "problem using a very specific defense. See if you can identify it in the next defense")
        goto(CoverWord1Goto)
    }

    onResponse<DenialBlock1> {
        furhat.say(" Not really. The patient is not denying that there is something troubling them so it is " +
                " not a question of denial in this case. Try again with the next defense")
        goto(CoverWord1Goto)
    }

    onResponse<ProjectionBlock1> {
        furhat.say(" Good idea. The patient does mention other people which hints at projection. While the patient " +
                " may be projecting they are also avoiding their emotions in a very particular way in this scenario. Try" +
                " to spot it in the next similar defense")
        goto(CoverWord1Goto)
    }

    onResponse<RationalizationBlock1> {
        furhat.say(" Sensible guess. The patient does reason a bit about the cause of their emotions, which is a " +
                " sign of rationalization. There is something more specific at play in this case however. Try again" +
                " in the next defense of the same category.")
        goto(CoverWord1Goto)
    }
}


val Rationalization1Goto : State = state {
    onEntry {
        goto(Rationalization1)
    }
}

val Rationalization1 : State = state {

    val rand = Random()
    val num = rand.nextInt(5)
    onEntry {
        when (num) {
            0 -> furhat.ask(" If my dad just didn't bring up certain topics I wouldn't get upset")
            1 -> furhat.ask(" I think the reason for my problem stems from when I was younger")
            2 -> furhat.ask(" I think it's because he has this strange voice that I get needlessly upset")
            3 -> furhat.ask(" It's only when I'm tired that I experience these problems so I just need to get more sleep")
            4 -> furhat.ask(" I noticed that my problem comes up whenever I'm also stressed from work so that might be a reason")
        }
    }

    onResponse<RationalizationBlock1> {

        it.intent.intellect
        it.intent.feel
        it.intent.notice
        it.intent.avoid
        it.intent.person
        it.intent.negative
        it.intent.problem

        when (num) {
            0 -> furhat.say(" Yes correct. Rationalization means the patient describes reasons for their " +
                    "problem rather than the experience of their emotions")
            1 -> furhat.say("Yes this is rationalization. By analyzing the underlying reasons for their " +
                    "feeling rather than exploring the emotions directly, the patient is distancing himself from " +
                    "the problem.")
            2 -> furhat.say(" Excellent. Rationalization can often be identified by words like 'think', 'reason' " +
                    " or 'because' so they are a good hint the patient is rationalizing.")
            3 -> furhat.say(" Exactly right. Keep in mind that even if the patients analysis of the problem " +
                    "is reasonable or correct, they are nevertheless distancing themselves from their emotions")
            4 -> furhat.say(" Good job. In order to block rationalization, help the patient differentiate " +
                    "their reasons from feelings and then help them explore the feelings directly")
        }
        goto(Wait1)
    }

    onResponse<Hint> {
        furhat.say(" Does the patient talk directly about their feelings? See what they talk about instead of exploring" +
                "their emotions.")
        furhat.say(" The next defense will be of the same kind. Be attentive to what the patient does instead of" +
                " sharing their feelings")
        goto(Rationalization1Goto)
    }

    onResponse<TryAgain> {
        furhat.say("Sure. Let's go with another defense, I'm sure you will get this one.")
        goto(DeclareProblem)
    }

    onResponse<GiveAnswer> {
        furhat.say("It was rationalization. Whenever patients provide reasons for their problem rather than" +
                " talking directly about their emotions they may be rationalizing. ")
        furhat.say(" Try again with the next defense coming up")
        goto(DeclareWait)
    }

    onResponse<VagueBlock1> {
        furhat.say(" A reasonable guess. The patient is not very specific about their issue which is indeed vague. " +
                " However, in what way are they avoiding being direct about there issue? See if you can identify it " +
                " in the next defense")
        goto(Rationalization1Goto)
    }

    onResponse<DenialBlock1> {
        furhat.say(" Not really. Notice the patient is rather open about having an issue. This indicates is's likely " +
                " not a question of denial. Try again with the next defense of the same category.")
        goto(Rationalization1Goto)
    }

    onResponse<ProjectionBlock1> {
        furhat.say(" Not quite. There may be some hints of projection since the patient is deflecting from their problem " +
                "onto other matters. Try to spot exactly what the patient is doing instead of describing their feelings " +
                " in this next similar defense.")
        goto(Rationalization1Goto)
    }

    onResponse<CoverWordBlock1> {
        furhat.say(" Not really. Cover word suggests the patient is using weaker emotional words to hide their " +
                " more intense feelings. This is not really the case here, give it another try.")
        goto(Rationalization1Goto)
    }
}


val Resolution1 : State = state {

    onEntry {
        furhat.ask(" Great job! You got the patient to declare an internal problem which, is the anger they " +
                "experience when talking to their father. You have successfully completed the first module and first " +
                "part of establishing a therapeutic alliance. If you would like to go over this module again say repeat. " +
                "If you would like to continue to the next module say continue.")
    }

    onResponse<Repeat> {
        counter1 = 0
        goto(FirstModule)
    }

}
