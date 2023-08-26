module Main (main) where
import Data.Maybe (fromMaybe)
import System.Console.GetOpt (ArgDescr(..), ArgOrder(..), OptDescr(..), getOpt)
import System.Environment (getArgs)
import Text.Read (readMaybe)

import Interp (Conf(name), initial)
import Dibujos.Ejemplo (ejemploConf)
import Dibujos.Feo (feoConf)
import Dibujos.Grilla (grillaConf)
import Dibujos.Escher (escherConf)
import Dibujos.Fractal (fractalConf)

-- Lista de configuraciones de los dibujos
configs :: [Conf]
configs = [ejemploConf, feoConf, grillaConf, escherConf, fractalConf]


dibujar :: [Conf] -> String -> IO ()
dibujar [] n = putStrLn "No hay dibujos disponibles"
dibujar [x] n =
    if n == name x then
        initial x 600
    else do
        putStrLn "Los dibujos disponibles son:"
        printLista configs
        putStr "Ingrese que dibujo quiere dibujar: "
        n <- getLine
        dibujar configs n

dibujar (x:xs) n =
    if n == name x then
        initial x 600
    else
        dibujar xs n


printLista :: [Conf] -> IO ()
printLista xs = putStrLn $ getString xs
    where
        getString [] = ""
        getString (x:xs) = "> "++name x++"\n"++ getString xs

main :: IO ()
main = do
    args <- getArgs

    if null args then do
        putStrLn "Invalid argument"
        putStrLn "Expecting <Dibujo Name>"

    else

        if head args ==  "--lista" then
            printLista configs
        else
            dibujar configs (head args)
