import Test.HUnit
import Dibujo
-- Para correr los test, hay que comentar las lineas 3 a 8

-- Defino una funcion auxiliar para MAPDIB
g :: Int -> Dibujo (Int,Int)
g n = figura (n,n)

-- TESTS:
-- CONSTRUCTORES:
testFigura_1 = TestCase (assertEqual "for (figura 5)," (Basico 5) (figura 5))
testFigura_2 = TestCase (assertEqual "for (figura ([1,2]))," (Basico [1,2]) (figura [1,2]))

testRotar_1 = TestCase (assertEqual "for (rotar (figura 5))," (Rotar (Basico 5)) (rotar (figura 5)))
testRotar_2 = TestCase (assertEqual "for (rotar (figura ([1,2])))," (Rotar (Basico [1,2])) (rotar (figura [1,2])))

testEspejar_1 = TestCase (assertEqual "for (espejar (figura 5))," (Espejar (Basico 5)) (espejar (figura 5)))
testEspejar_2 = TestCase (assertEqual "for (espejar (figura ([1,2])))," (Espejar (Basico [1,2])) (espejar (figura [1,2])))

testRot45_1 = TestCase (assertEqual "for (rot45 (figura 5))," (Rot45 (Basico 5)) (rot45 (figura 5)))
testRot45_2 = TestCase (assertEqual "for (rot45 (figura ([1,2])))," (Rot45 (Basico [1,2])) (rot45 (figura [1,2])))

testApilar_1 = TestCase (assertEqual "for (apilar 100 100 (figura 5) (figura 6)),"  
                (Apilar 100.0 100.0 (Basico 5) (Basico 6)) (apilar 100 100 (figura 5) (figura 6)))
testApilar_2= TestCase (assertEqual "for (apilar 100 100 (figura ([1,2])) (figura ([1,2,3]))),"  
                (Apilar 100.0 100.0 (Basico [1,2]) (Basico [1,2,3])) (apilar 100 100 (figura [1,2]) (figura [1,2,3])))

testJuntar_1 = TestCase (assertEqual "for (juntar 100 100 (figura 5) (figura 6))," 
                (Juntar 100.0 100.0 (Basico 5) (Basico 6)) (juntar 100 100 (figura 5) (figura 6)))
testJuntar_2 = TestCase (assertEqual "for (juntar 100 100 (figura ([1,2])) (figura ([1,2,3])))," 
                (Juntar 100.0 100.0 (Basico [1,2]) (Basico [1,2,3])) (juntar 100 100 (figura [1,2]) (figura [1,2,3])))

testEncimar_1 = TestCase (assertEqual "for (encimar (figura 5) (figura 6))," 
                (Encimar (Basico 5) (Basico 6)) (encimar (figura 5) (figura 6)))
testEncimar_2 = TestCase (assertEqual "for (encimar (figura ([1,2])) (figura ([1,2,3])))," 
                (Encimar (Basico [1,2]) (Basico [1,2,3])) (encimar (figura [1,2]) (figura [1,2,3])))

-- REDEFINICIONES 
testJuntar_3 = TestCase (assertEqual "for ((.-.) (figura 5) (figura 6))," 
                (Juntar 100 100 (Basico 5) (Basico 6))  ((.-.) (Basico 5) (Basico 6)))
testJuntar_4 = TestCase (assertEqual "for ((.-.) (figura 5) (figura 6))," 
                (Juntar 100 100 (Basico [1,2]) (Basico [1,2,3]))  ((.-.) (Basico [1,2]) (Basico [1,2,3])))

testApilar_3 = TestCase (assertEqual "for ((///) (figura 5) (figura 6))," 
                (Apilar 100 100 (Basico 5) (Basico 6))  ((///) (Basico 5) (Basico 6)))
testApilar_4 = TestCase (assertEqual "for ((///) (figura [1,2]) (figura [1,2,3]))," 
                (Apilar 100 100 (Basico [1,2]) (Basico [1,2,3]))  ((///) (Basico [1,2]) (Basico [1,2,3])))

testEncimar_3 = TestCase (assertEqual "for ((^^^) (figura 5) (figura 6))," 
                (Encimar (Basico 5) (Basico 6))  ((^^^) (Basico 5) (Basico 6)))
testEncimar_4 = TestCase (assertEqual "for ((^^^) (figura [1,2]) (figura [1,2,3]))," 
                (Encimar (Basico [1,2]) (Basico [1,2,3]))  ((^^^) (Basico [1,2]) (Basico [1,2,3])))

-- FUNCIONES DE ALTO NIVEL
testCuarteto = TestCase ( assertEqual "for cuarteto (figura 0) (figura 1) (figura 2) (figura 3)," 
                            (Apilar 100 100 (Juntar 100 100(Basico 0) (Basico 1)) (Juntar 100 100(Basico 2) (Basico 3))) 
                            (cuarteto (Basico 0) (Basico 1) (Basico 2) (Basico 3))
                        )

testEncimar4 = TestCase ( assertEqual "for encimar4 (figura 'a')," 
                            (Encimar (Encimar (Basico 'a') (Rotar (Basico 'a'))) (Encimar (Rotar (Rotar (Basico 'a'))) (Rotar (Rotar (Rotar (Basico 'a')))))) 
                            (encimar4 (Basico 'a'))
                        )

testCiclar = TestCase   ( assertEqual "for ciclar (figura ('1','a'))," 
                            (Apilar 100 100 (Juntar 100 100 (Basico ('1','a')) (Rotar (Basico ('1','a')))) (Juntar 100 100 (Rotar (Rotar (Basico ('1','a')))) (Rotar (Rotar (Rotar (Basico ('1','a')))))))
                            (ciclar (Basico ('1','a')))
                        )

testFoldDib = TestCase  ( assertEqual "for figuras (ciclar (figura ('1','a')))," 
                            [('1','a'),('1','a'),('1','a'),('1','a')] 
                            (figuras (ciclar (Basico ('1','a'))))
                        )


testMapDib = TestCase   ( assertEqual "for mapDib g (cuarteto (figura 1) (figura 2) (figura 3) (figura 4)),"
                            (Apilar 100 100 (Juntar 100 100 (Basico (1,1)) (Basico (2,2))) (Juntar 100 100 (Basico (3,3)) (Basico (4,4))))
                            (mapDib g (cuarteto (figura 1) (figura 2) (figura 3) (figura 4)))
                        )

testDibujo = TestList [ TestLabel "testFigura 1" testFigura_1,  TestLabel "testFigura 2" testFigura_2,    
                        
                        TestLabel "testRotar 1" testRotar_1,    TestLabel "testRotar 2" testRotar_2,
                        
                        TestLabel "testEspejar 1" testEspejar_1,TestLabel "testEspejar 2" testEspejar_2,  
                        
                        TestLabel "testRot45 1" testRot45_1,    TestLabel "testRot45 2" testRot45_2,
                        
                        TestLabel "testApilar 1" testApilar_1,  TestLabel "testApilar 2" testApilar_2,    
                        TestLabel "testApilar 3" testApilar_3,  TestLabel "testApilar 4"  testApilar_4,   
                        
                        TestLabel "testJuntar 1" testJuntar_1,  TestLabel "testJuntar 2" testJuntar_2,
                        TestLabel "testJuntar 3" testJuntar_3,  TestLabel "testJuntar 4" testJuntar_4,
                        
                        TestLabel "testEncimar 1" testEncimar_1,TestLabel "testEncimar 2" testEncimar_2,  
                        TestLabel "testEncimar 3" testEncimar_3,TestLabel "testEncimar 4" testEncimar4,
                        
                        TestLabel "testCuarteto" testCuarteto,    
                        
                        TestLabel "testEncimar4"  testEncimar4,   
                        
                        TestLabel "testCiclar" testCiclar,        
                       
                        TestLabel "testFoldDib" testFoldDib,
                        
                        TestLabel "testMapDib" testMapDib ]

-- Ejecutar los casos de prueba
main = runTestTT testDibujo
