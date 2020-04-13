import java.util.UUID

object TestDependentTypes extends App{
  val myConf = RealConf(UUID.randomUUID(), RealSettings(RealData(5, 25.0)))

  RealConfLoader(7).extractData(myConf.settings)

  def processor(confLoader: ConfLoader, conf: Conf) = confLoader.extractData(conf.settings.asInstanceOf[confLoader.T])
}

trait Data

case class RealData(anInt: Int, aDouble: Double) extends Data

trait MySettings

case class RealSettings(data: RealData) extends MySettings

trait Conf {
  def id: UUID
  def settings: MySettings
}

case class RealConf(id: UUID, settings: RealSettings) extends Conf

trait ConfLoader{
  type T <: MySettings
  def extractData(settings: T): Data
}

case class RealConfLoader(someInfo: Int) extends ConfLoader {
  type T = RealSettings
  override def extractData(settings: RealSettings): RealData = settings.data
}
