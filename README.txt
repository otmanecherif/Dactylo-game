Le code du projet contient 4 éléments importants qui sont l'interface principale, les conditions de jeu, quelques animations et popup pour le score et les résultats, ainsi qu'une option qui enregistre votre vitesse maximale, votre précision et votre nombre de vies.

Dans ce dossier, j'expliquerai comment utiliser l'application à partir de zéro et ce dont vous avez besoin pour la lancer.

Exigences : 
- Eclipse / Intelij 
- Java 18 
- Fichiers de librairie JavaFx 19 
- Les fichiers Txt contiennent des mots aléatoires (le nombre de mots est illimité). 

Pour lancer l'application, vous devez suivre les étapes suivantes : 
- Tout d'abord ajouter les libs JavaFx en utilisant File > Project structure > Libraries > Add all files of javafx existed in folder lib . also add this in VM parameters : java --module-path "Path to your javafx lib folder" --add-modules javafx.controls,javafx.fxml 
- Placez le fichier texte nommé "wordlist" dans le dossier racine. 
- Lancez l'application en utilisant Run ou vous pouvez le faire en utilisant la ligne de commande dans le dossier racine E.g java --module-path D:\javafx-sdk-19.0.2\lib --add-modules javafx.controls app

Une fois que l'application est lancée, vous pouvez commencer à jouer, pour rendre le jeu plus amusant et plus difficile, vous avez l'option de temps dans votre côté qui est le mode difficile par exemple, vous pouvez régler le temps à 2 minutes et commencer à jouer. 

Instructions pour jouer :

1- Lancez l'application en utilisant des commandes ou en utilisant l'IDE  
2- Une fois l'application lancée, si c'est la première fois, vous devez introduire votre nom. 
3- Après cela, vous pouvez cliquer sur n'importe quel bouton, l'application vous dirigera vers le jeu. 
4- Le jeu ne commencera pas avant que vous ne commenciez à taper, avec chaque monde correct vous obtenez des points et vous ne perdez pas une de vos vies. Si vous obtenez le mot bleu et vous tapez correctement c'est +1 à votre vie. 
5- Il y a un compteur qui calcule chaque mot correct, chaque fois que vous faites 100 ou un multiple de 100 mots corrects, vous passez au niveau suivant.
6- Pour chaque niveau, les mots apparaîtront à une vitesse plus rapide. 
7- La vitesse est calculée en fonction de votre niveau et du nombre de mots corrects en utilisant une fonction descendante comme mentionné dans le document du projet. 
8 - les mots s'effacent en fonction de la vitesse, au début, cela peut prendre plus de 10 secondes, puis cela change toutes les secondes, vous devez également garder un œil sur le chronomètre. 
9- Le jeu contient beaucoup de texte affiché Accuarcy, Lives, Seconds, Speed. 
10 - A la fin du jeu, l'application sauvegardera les fichiers texte contenant toutes ces informations avec votre nom dessus. 

Fonctionnalités : 
- Pour que le jeu continue à fonctionner, nous utilisons la fonction Run du scheduler qui fonctionne à chaque seconde. 
- Pour garder le jeu plus efficace et sans aucune opération lente, il y a plusieurs threads, un pour mettre à jour le score, un autre pour mettre à jour les vies ... 
- Pour ajuster le temps, nous utilisons la bibliothèque Timer. 
- Pour calculer la vitesse, nous avons fait le calcul dans le run pour qu'il fonctionne exactement avec tous les Threads. 

- Pour tester l'application, il y a déjà un fichier texte contenant des mots compliqués. 




Traduit avec www.DeepL.com/Translator (version gratuite)