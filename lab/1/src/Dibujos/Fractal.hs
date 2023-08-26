module Dibujos.Fractal
  ( fractalConf,
  )
where

import Dibujo (Dibujo, encimar4, apilar, juntar, cuarteto, espejar, encimar, figura, rotar, rot45, r180, r270)
import FloatingPic (Output, half, zero)
import Graphics.Gloss (Picture (Blank), blue, color, line, pictures, red, white, polygon)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Grilla (grilla)
import Interp (Conf (..), interp)

type Fractal = Bool

-- Dibujos Basicos
blank :: Dibujo Fractal
blank = figura False

square :: Dibujo Fractal
square = figura True

-- Construccion del dibujo Fractal
nonet ::  Dibujo Fractal-> Dibujo Fractal-> Dibujo Fractal ->
          Dibujo Fractal-> Dibujo Fractal-> Dibujo Fractal ->
          Dibujo Fractal-> Dibujo Fractal-> Dibujo Fractal ->
          Dibujo Fractal
nonet p q r s t u v w x = grilla [[p, q, r],
                                  [s, t, u],
                                  [v, w, x]]

fractal :: Int -> Dibujo Fractal
fractal 0 = square
fractal n = nonet (fractal $ n-1) (fractal $ n-1) (fractal $ n-1)
                  (fractal $ n-1) square          (fractal $ n-1)
                  (fractal $ n-1) (fractal $ n-1) (fractal $ n-1)

interpBas :: Output Fractal
interpBas False x y w = Blank
interpBas True x y w = line [x, x V.+ y, x V.+ y V.+ w, x V.+ w, x]

-- Configuración del dibujo Fractal
fractalConf :: Conf
fractalConf =
  Conf
    { name = "Fractal",
      pic = interp interpBas  (fractal 5)
    }