package org.allenai.parse

import org.scalatest._

class StanfordParserSpec extends FlatSpecLike with Matchers {

  val parser = new StanfordParser

  "parseSentence" should "give correct dependencies and part of speech tags" in {
    val sentence = "People eat good food."
    val parse = parser.parseSentence(sentence)

    val dependencies = parse.getDependencies
    dependencies.size should be(4)
    dependencies.toSet should be(Set(
      Dependency("eat", 2, "People", 1, "nsubj"),
      Dependency("eat", 2, "food", 4, "dobj"),
      Dependency("food", 4, "good", 3, "amod"),
      Dependency("ROOT", 0, "eat", 2, "root")))

    val posTags = parse.getPosTags
    posTags.size should be(5)
    posTags(0) should be(PartOfSpeech("People", "NNS"))
    posTags(1) should be(PartOfSpeech("eat", "VBP"))
    posTags(2) should be(PartOfSpeech("good", "JJ"))
    posTags(3) should be(PartOfSpeech("food", "NN"))
    posTags(4) should be(PartOfSpeech(".", "."))
  }

  it should "give collapsed dependencies" in {
    val sentence = "Mary went to the store."
    val parse = parser.parseSentence(sentence)

    val dependencies = parse.getDependencies
    dependencies.size should be(4)
    dependencies.toSet should be(Set(
      Dependency("went", 2, "Mary", 1, "nsubj"),
      Dependency("went", 2, "store", 5, "prep_to"),
      Dependency("store", 5, "the", 4, "det"),
      Dependency("ROOT", 0, "went", 2, "root")))

  }
}
