package barneshut

import org.scalatest.FunSuite

class SimulatorTest extends FunSuite {

  val model = new SimulationModel

  val sim = new Simulator(model.taskSupport, model.timeStats)

  test("testComputeSectorMatrix") {
    val bodies = Seq(new Body(1, 25, 37, 0.1f, 0.1f), new Body(2, 22, 41, 0.1f, 0.1f))
    val boundaries = new Boundaries
    boundaries.minX = 1
    boundaries.minY = 1
    boundaries.maxX = 97
    boundaries.maxY = 97
    val result = sim.computeSectorMatrix(bodies, boundaries)

    assert(result(2, 3).size == 1)
    assert(result(2, 3).exists(_ == bodies.head))
    assert(result(1, 3).size == 1)
    assert(result(1, 3).exists(_.mass == 2))
  }

  test("testMergeBoundaries") {
    val b1 = new Boundaries()
    b1.minX = 1
    b1.maxX = 5
    b1.minY = 10
    b1.maxY = 20

    val b2 = new Boundaries()
    b2.minX = 2
    b2.maxX = 6
    b2.minY = 9
    b2.maxY = 19
    val result = sim.mergeBoundaries(b1, b2)

    assert(result.minX == 1)
    assert(result.maxX == 6)
    assert(result.minY == 9)
    assert(result.maxY == 20)
  }

  test("testUpdateBoundaries") {
    val result = sim.updateBoundaries(new Boundaries(), new Body(1, 20, 30, 0, 0))
    val result2 = sim.updateBoundaries(result, new Body(1, 21, 29, 0, 0))
    assert(result2.minX == 20)
    assert(result2.maxX == 21)
    assert(result2.minY == 29)
    assert(result2.maxY == 30)
  }

}
