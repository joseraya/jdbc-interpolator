package com.agilogy.jdbc.interpolation


case class SetableParameter[T](value:T, mapper: JdbcParameterCompatible[T])
case class SqlStatement(source:String, vars: Seq[SetableParameter[_]]) {

}
