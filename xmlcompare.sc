// scala 2.13.3

import $ivy.`software.purpledragon.xml:xml-compare_2.13:2.0.0`, software.purpledragon.xml.compare._
import scala.xml.XML
import scala.xml.Elem
import scala.xml.Node

@main
def main(oldFile: String, newFile: String): Unit = {
  println(s"comparing $oldFile to $newFile")

  val oldXml = XML.loadFile(oldFile)
  val newXml = XML.loadFile(newFile)

  val diff = XmlCompare.compare(transform(oldXml), transform(newXml))
  println(diff)
}

def transform(e: Node): Elem = e match {
  case e@Elem(prefix, label, atts, scope, children) =>
    Elem(
      prefix,
      label,
      atts,
      scope,
      children.sortWith { case (left, right) =>
        left.label.compareTo(right.label) <= 0
      }.map(transform):_*
    )
  case other =>
    println("JACOB WHAT" + other)
    throw new Exception("")
}
