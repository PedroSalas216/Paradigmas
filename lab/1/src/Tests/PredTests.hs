import Test.HUnit
import Pred
import Dibujo
-- Para correr los test, hay que comentar las lineas 2 y 3

-- Defifniciones auxiliares 
isZero :: Pred Int
isZero = (==0)

underTen :: Pred Int
underTen = (<10)

underSix :: Pred Int
underSix = (<6)

dibujo1 = (.-.) (figura 10) ((.-.) (figura 9) (figura 0))
dibujo2 = figura 6

rotarPred :: Int -> Dibujo Int

-- TESTS:
testCambiar = TestCase  ( assertEqual "for cambiar underTen rotarPred ((.-.) (figura 10) ((.-.) (figura 9) (figura 0))),"
                            (Juntar 100 100 (Basico 10) (Juntar 100 100 (Rotar (Basico 9)) (Rotar (Basico 0))))
                            (cambiar underTen rotarPred dibujo1)
                        )

testAnyFig = TestCase   ( assertEqual "for anyFig underTen ((.-.) (figura 10) ((.-.) (figura 9) (figura 0))),"
                            True
                            (anyFig underTen dibujo1)
                        )


testAllFig = TestCase   ( assertEqual "for allFig isZero ((.-.) (figura 10) ((.-.) (figura 9) (figura 0))),"
                            False
                            (allFig isZero dibujo1)
                        )


testAndP = TestCase     ( assertEqual "for andP underTen underSix 6,"
                            False
                            (andP underTen underSix 6)
                        )

testOrP = TestCase      ( assertEqual "for orP underTen underSix 6,"
                            True
                            (orP underTen underSix 6)
                        )

testPred = TestList [ TestLabel "testCambiar" testCambiar,
                      TestLabel "testAnyFig" testAnyFig, 
                      TestLabel "testAllFig" testAllFig,
                      TestLabel "testAndP" testAndP,
                      TestLabel "testOrP" testOrP ]

main = runTestTT testPred