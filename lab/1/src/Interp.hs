module Interp (
    interp,
    Conf(..),
    interpConf,
    initial
) where

import Graphics.Gloss(Picture, Display(InWindow), makeColorI, color, pictures, translate, white, display)
import Dibujo (Dibujo, foldDib)
import FloatingPic (FloatingPic, Output, grid, zero, half)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

-- -- -- Interpretacion de un dibujo
-- -- -- formulas sacadas del enunciado
-- a -> flp -> dibujo a -> flp
interp :: Output a -> Output (Dibujo a)
interp interpBas = foldDib interpBas interpRotar interpEspejar interpRot45 interpApilar interpJuntar interpEncimar


-- rotar(f)(x, w, h) ->  f(x+w, h, -w)
interpRotar :: FloatingPic -> FloatingPic
interpRotar flp x w h = flp (x V.+ w) h (zero V.- w)

-- rot45(f)(x, w, h) -> f(x+(w+h)/2, (w+h)/2, (h-w)/2)
interpRot45 :: FloatingPic -> FloatingPic
interpRot45 flp x w h =  flp (x V.+ half(w V.+ h)) (half(w V.+ h)) (half (h V.- w))

-- espejar(f)(x, w, h) -> f(x+w, -w, h)
interpEspejar :: FloatingPic -> FloatingPic
interpEspejar flp x w = flp (x V.+ w) (zero V.- w)


-- apilar(n, m, f, g) (x, w, h) -> f(x + h', w, r*h) ∪ g(x, w, h') 
--     con r' = n/(m+n), r=m/(m+n), h'=r'*h
interpApilar :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
interpApilar n m flp1 flp2 x w h =  pictures [flp1 (x V.+h') w (r V.*h),flp2 x w h' ]
    where
        r' = n / (m+n)
        r  = m / (m+n)
        h' = r' V.*h


-- juntar(n, m, f, g)(x, w, h) -> f(x, w', h) ∪ g(x+w', r'*w, h) con 
--     con r'=n/(m+n), r=m/(m+n), w'=r V.*w
interpJuntar :: Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic
interpJuntar n m flp1 flp2 x w h = pictures [flp1 x w' h, flp2 (x V.+ w') (r' V.*w) h]
    where
        r' = n / (m + n)
        r  = m / (m + n)
        w' = r V.* w

-- encimar(f,g)(x, w, h) -> f(x, w, h) ∪ g(x, w, h)
interpEncimar :: FloatingPic -> FloatingPic -> FloatingPic
interpEncimar flp1 flp2 x w h = pictures [flp1 x w h, flp2 x w h]


-- -------------------------- NO TOCAR -------------------------------



-- Configuración de la interpretación
data Conf = Conf {
        name :: String,
        pic :: FloatingPic
    }

interpConf :: Conf -> Float -> Float -> Picture
interpConf (Conf _ p) x y = p (0, 0) (x,0) (0,y)

-- Dada una computación que construye una configuración, mostramos por
-- pantalla la figura de la misma de acuerdo a la interpretación para
-- las figuras básicas. Permitimos una computación para poder leer
-- archivos, tomar argumentos, etc.
initial :: Conf -> Float -> IO ()
initial cfg size = do
    let n = name cfg
        win = InWindow n (ceiling size, ceiling size) (0, 0)
    display win white $ withGrid (interpConf cfg size size) size size

--   where withGrid p x y = translate (-size/2) (-size/2) $ pictures [p, color grey $ grid (ceiling $ size / 10) (0, 0) x 10]
        -- grey = makeColorI 120 120 120 120
  where withGrid p x y = translate (-size/2) (-size/2) $ p