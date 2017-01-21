package com.agilogy.jdbc.interpolation


object JdbcInterpolation {

  implicit class SqlInterpolation(val sc: StringContext) extends AnyVal {

    def sql(): SqlStatement = {
      statement()
    }

    def sql[T](arg: T)(implicit mapper: JdbcParameterCompatible[T]): SqlStatement = {
      statement(param(arg))
    }

    def sql[T, U](arg1: T, arg2:U)(implicit mapper1: JdbcParameterCompatible[T], mapper2: JdbcParameterCompatible[U]): SqlStatement = {
      statement(param(arg1), param(arg2))
    }

    private def statement(args: SetableParameter[_]*): SqlStatement = {
      SqlStatement(statementWithPlaceHolders, args)
    }

    private def param[T](arg: T)(implicit mapper: JdbcParameterCompatible[T]): SetableParameter[T] = {
      SetableParameter(arg, mapper)
    }

    private def statementWithPlaceHolders: String = {
      val strings = sc.parts.iterator
      val builder = new StringBuilder
      while (strings.hasNext) {
        val str = strings.next()
        builder.append(str)
        //We can do this because if an arg is the last part of the string
        //there will be an empty string as next string
        if (strings.hasNext) {
          builder.append("?")
        }
      }
      builder.mkString.trim
    }
  }
}
