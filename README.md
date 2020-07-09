# pokemon-bat

The Story
In our story, we have several objects - Pokemon, Trainer and Battle.
we’ll go over them one by one:
## Pokemon
The main object that we’ll use is called Pokemon.
Each Pokemon has a name and a type. The type can be one of the following:

● Fire

● Water

● Grass

Every type has its own strength, by these rules:

● Water is stronger than Fire!

● Fire is stronger than Grass!

● Grass is stronger than Water!

## Trainer
Each Trainer has a name, level (number), and a bag of Pokemons.
The bag of Pokemons has room for up to 3 Pokemons.
When a Trainer catches new Pokemon he puts it in the bag.
If the bag already has 3 Pokemons in it, And the trainer tries adding another one, then one of
the existing Pokemons is set free.
## Battle
Each Trainer can battle other trainers with his Pokemons.
Each Pokemon battle is 3 Pokemons vs 3 Pokemons.
If one of the trainers has less than 3 Pokemons then the battle is canceled.
The winner of the battle will gain 2 level points.
If Pokemons are from the same type it’s a tie, and each trainer gets 1 level point.
In the battle, every Pokemon in the bag will fight with the Pokemon in the same position on the
other Trainer’s bag. The Trainer that most of his Pokemons won is the winner of the battle.

This Project is based on Spring Boot, and I use H2 SQL DB

I Also support API Call such as:

● GET http://localhost:<port>/battle/{trainer1_name}/{trainer2_name}
  
● GET http://localhost:<port>/trainer/{trainer_name}
  
● GET http://localhost:<port>/trainer/{trainer_name}/catch/{pokemon_name}

● GET http://localhost:<port>/trainers


