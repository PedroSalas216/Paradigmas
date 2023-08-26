module Dibujos.Grilla (
    interpBas,
    grillaConf
) where
    
import Graphics.Gloss ( Picture, scale, text, translate )
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Dibujo (Dibujo, figura)
import FloatingPic (Output, half, zero)
import Interp (Conf(..), interp)
import Grilla (grilla)

type Basica = (Int, Int)

-- Construccion de la grilla
makeCol :: Int -> [[Dibujo Basica]]
makeCol n = map makeRow [0,1..n]
  where 
      makeRow x = map (figura . (\y -> (x, y))) [0,1..n]

makeGrilla :: Dibujo Basica
makeGrilla = grilla (makeCol 7)

interpBas :: Show a => a -> (Float, Float) -> p1 -> p2 -> Picture
interpBas tuple (x, y) _ _ = translate (x+10) (y+10) $ scale 0.1 0.1 (text (show tuple))

-- Configuracion de la grilla 
grillaConf :: Conf
grillaConf = 
    Conf {
      name = "Grilla",
      pic = interp interpBas makeGrilla
      }
