module Dibujo
    ( Dibujo,
    figura, rotar, espejar, rot45, apilar, juntar, encimar,
    r180, r270,
    (.-.), (///), (^^^),
    cuarteto, encimar4, ciclar,
    foldDib, mapDib, figuras )
where
import Data.Binary.Put (putBuilder)
import GHC.Base (mapFB)


data Dibujo a =  Basico a
    | Rotar (Dibujo a)
    | Espejar (Dibujo a)
    | Rot45 (Dibujo a)
    | Apilar Float Float (Dibujo a) (Dibujo a)
    | Juntar Float Float (Dibujo a) (Dibujo a)
    | Encimar (Dibujo a) (Dibujo a)
    deriving (Eq, Show)

-- Composición n-veces de una función con sí misma.
comp        :: (a -> a) -> Int -> a -> a
comp f 0 x  = x
comp f n x  = comp f (n-1) (f x)

-- Rotaciones de múltiplos de 90. (r90 fue una implementacion )
r180    :: Dibujo a -> Dibujo a
r180    = comp Rotar 2

r270    :: Dibujo a -> Dibujo a
r270    = comp Rotar 3

-- Construcción de dibujo. Abstraen los constructores.
figura  :: a -> Dibujo a
figura  = Basico

rotar   :: Dibujo a -> Dibujo a
rotar   = Rotar

espejar :: Dibujo a -> Dibujo a
espejar = Espejar

rot45   :: Dibujo a -> Dibujo a
rot45   = Rot45

apilar  :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
apilar  = Apilar

juntar  :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
juntar  = Juntar

encimar :: Dibujo a -> Dibujo a -> Dibujo a
encimar = Encimar


-- Funciones a partir de los constructores.

-- Funciones con valores standard.
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) = juntar 100 100

(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) = apilar 100 100

(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) = encimar

-- Funciones de alto nivel.
cuarteto          :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto a b c d  = (///) ((.-.) a b) ((.-.) c d)

encimar4          :: Dibujo a -> Dibujo a
encimar4 a        = (^^^) ((^^^) a (rotar a)) ((^^^) (r180 a) (r270 a))

ciclar            :: Dibujo a -> Dibujo a
ciclar a          = cuarteto a (rotar a) (r180 a) (r270 a)

foldDib :: (a -> b) -> (b -> b) -> (b -> b) -> (b -> b) ->
       (Float -> Float -> b -> b -> b) ->
       (Float -> Float -> b -> b -> b) ->
       (b -> b -> b) ->
       Dibujo a -> b
foldDib bas rot esp r45 api jun enc (Basico a)        = bas a
foldDib bas rot esp r45 api jun enc (Rotar a)         = rot (foldDib bas rot esp r45 api jun enc a)
foldDib bas rot esp r45 api jun enc (Espejar a)       = esp (foldDib bas rot esp r45 api jun enc a)
foldDib bas rot esp r45 api jun enc (Rot45 a)         = r45 (foldDib bas rot esp r45 api jun enc a)
foldDib bas rot esp r45 api jun enc (Apilar x y a b)  = api x y (foldDib bas rot esp r45 api jun enc a)
                                                                (foldDib bas rot esp r45 api jun enc b)
foldDib bas rot esp r45 api jun enc (Juntar x y a b)  = jun x y (foldDib bas rot esp r45 api jun enc a)
                                                                (foldDib bas rot esp r45 api jun enc b)
foldDib bas rot esp r45 api jun enc (Encimar a b)     = enc (foldDib bas rot esp r45 api jun enc a)
                                                            (foldDib bas rot esp r45 api jun enc b)

mapDib :: (a -> Dibujo b) -> Dibujo a -> Dibujo b
mapDib f (Basico a)       = f a
mapDib f (Rotar a)        = Rotar (mapDib f a)
mapDib f (Espejar a)      = Espejar (mapDib f a)
mapDib f (Rot45 a)        = Rot45 (mapDib f a)
mapDib f (Apilar x y a b) = Apilar x y (mapDib f a) (mapDib f b)
mapDib f (Juntar x y a b) = Juntar x y (mapDib f a) (mapDib f b)
mapDib f (Encimar a b)    = Encimar (mapDib f a) (mapDib f b)


{- Funciones auxiliares que originalmente usamos en figuras, 
-- que despues de haber terminado el resto del proyecto,
-- cambiamos por la implementacion de abajo

juntar0 :: a -> [a]
juntar0 a = [a]

juntar1 :: a -> a
juntar1 a = a

juntar2 :: Float -> Float -> [a] -> [a] -> [a]
juntar2 a b xs ys = xs ++ ys

juntar3 :: [a] -> [a] -> [a]
juntar3 xs ys = xs ++ ys 
-}

figuras :: Dibujo a -> [a]
figuras  = foldDib (: []) id id id (\_ _ a b -> a ++ b) (\_ _ a b -> a ++ b) (++)
