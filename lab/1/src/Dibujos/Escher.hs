module Dibujos.Escher
  ( escherConf,
  )
where

import Dibujo (Dibujo, encimar4, apilar, juntar, cuarteto, espejar, encimar, figura, rotar, rot45, r180, r270)
import FloatingPic (Output, half, zero)
import Graphics.Gloss (Picture (Blank), blue, color, line, pictures, red, white, polygon)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Grilla (grilla)
import Interp (Conf (..), interp)

type Escher = Bool

-- Dibujos Basicos
blank :: Dibujo Escher
blank = figura False

fish :: Dibujo Escher
fish = figura True

fish2 :: Dibujo Escher
fish2 = espejar (rot45 fish)

fish3 :: Dibujo Escher
fish3 = r270 fish2

tileT :: Dibujo Escher
tileT = encimar fish (encimar fish2 fish3)

tileU :: Dibujo Escher
tileU = encimar4 fish2

-- Funciones Recursivas para la construccion del grafico de Escher
side :: Int -> Dibujo Escher
side 0 = blank
side n = cuarteto (side (n-1)) (side (n-1)) (rotar tileT) tileT

corner :: Int -> Dibujo Escher
corner 0 = blank
corner n = cuarteto (corner (n-1)) (side (n-1)) (rotar(side (n-1))) tileU


-- Construccion del dibujo de Escher
nonet :: Dibujo Escher-> Dibujo Escher-> Dibujo Escher ->
        Dibujo Escher-> Dibujo Escher-> Dibujo Escher ->
        Dibujo Escher-> Dibujo Escher-> Dibujo Escher ->
        Dibujo Escher
nonet p q r s t u v w x = grilla [[p, q, r],
                                  [s, t, u],
                                  [v, w, x]]

escher:: Int -> Dibujo Escher
escher n = nonet (corner n)      (side n)        (r270 (corner n))
                 (rotar(side n))   tileU           (r270 (side n))
                 (rotar(corner n)) (r180(side n))  (r180 (corner n))

interpBas :: Output Escher
interpBas False a b c = Blank
interpBas True a b c = pictures [line $ triangulo a b c, cara a b c]
  where
      triangulo a b c = map (a V.+) [zero, c, b, zero]
      cara a b c = polygon $ triangulo (a V.+ half c) (half b) (half c)

-- Configuración del dibujo de Escher
escherConf :: Conf
escherConf =
    Conf {
      name = "Escher",
      pic = interp interpBas (escher 10)
      }