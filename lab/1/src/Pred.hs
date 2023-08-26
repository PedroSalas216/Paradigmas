module Pred 
  ( Pred,
  cambiar, anyFig, allFig, orP, andP ) 
where
import Dibujo ( figura, Dibujo, foldDib, mapDib )

type Pred a = a -> Bool

cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar pred f = mapDib (\a -> if pred a then f a else figura a) 

anyFig :: Pred a -> Dibujo a -> Bool
anyFig pred = foldDib pred id id id (\a b x y -> x || y) (\a b x y -> x || y) (||)

allFig :: Pred a -> Dibujo a -> Bool
allFig pred = foldDib pred id id id (\a b x y -> x && y) (\a b x y -> x && y) (&&)

andP :: Pred a -> Pred a -> Pred a
andP pred1 pred2 elem = pred1 elem && pred2 elem

orP :: Pred a -> Pred a -> Pred a
orP pred1 pred2 elem = pred1 elem || pred2 elem
