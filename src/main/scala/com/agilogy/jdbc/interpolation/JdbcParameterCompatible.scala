package com.agilogy.jdbc.interpolation

import java.sql.PreparedStatement

trait JdbcParameterCompatible[T] {
  def set(ps:PreparedStatement, index:Int, value:T): Unit
}

object DefaultMappers {
  implicit val stringEvidence = new JdbcParameterCompatible[String] {
    override def set(ps: PreparedStatement, index: Int, value: String): Unit = {
      ps.setString(index, value)
    }
  }

  implicit val intEvidence = new JdbcParameterCompatible[Int] {
    override def set(ps: PreparedStatement, index: Int, value: Int): Unit = {
      ps.setInt(index, value)
    }
  }

}
