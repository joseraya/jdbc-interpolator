import com.agilogy.jdbc.interpolation.{DefaultMappers, SetableParameter}
import org.scalatest.FunSpec

class JdbcInterpolationSpec extends FunSpec{

  describe("Jdbc interpolation") {
    import com.agilogy.jdbc.interpolation.JdbcInterpolation._
    import com.agilogy.jdbc.interpolation.DefaultMappers._

    it("should work if there are no substitutions") {
      val ps = sql"select 2+2 from dual"

      assert(ps.source == "select 2+2 from dual")
    }

    it("should replace a var with a ? and keep its value") {
      val id = 1
      val ps = sql"select name from users where id=$id"

      assert(ps.source == "select name from users where id=?")
      assert(ps.vars == Seq(SetableParameter(id, DefaultMappers.intEvidence)))
    }

    it("should keep the order of the variables") {
      val id = 1
      val city = "BCN"
      val ps = sql"select name from users where id=$id and city=$city"

      assert(ps.source == "select name from users where id=? and city=?")
      assert(ps.vars == Seq(
        SetableParameter(id, DefaultMappers.intEvidence),
        SetableParameter(city, DefaultMappers.stringEvidence)
      ))
    }

  }
}
