# Slash Server​
​
A simple plugin to allow users to use the command /<servername> to transport them to the server.​
​
​
## Features:​
- Adds a command to your /<server name> which will teleport you to that server instantly.
- All commands are handled by permissions.
- All servers in your config get a command each so if you have a server named lobby /lobby will teleport you there.
- Each server can have a configurable delay before the player is sent there.
- Custom messages
​
## Permissions:​
**Permissions must be set up in the BungeeCord config.yml​**

`slashserver.[servername]` is the permission node and any players you wish to be able to use the command need this permission in bungeecord config.yml most likely under default group​
​

`slashserver.reload` allows the player to reload the SlashServer config


## Commands:
`/<servername>` - Transports the player to the server

`/ssreload` - Reloads the SlashServer config.

## Config:
The servers in the config have a number next to them, this number is the delay in miliseconds that a player will have to wait before it teleports them.

Vanilla server in the example will wait 5 seconds before teleporting the player there

```yml
Vanilla: 5000
Paintball: 0
SkyBlock: 0
Creative: 0
Spleef: 0
Hub: 0
ALREADY_ON_SERVER: '&cYou are already on that server!'
TELEPORTING: '&2Teleporting your to the server {name}'
ALREADY_TELEPORTING: '&cAlready teleporting you to a server'
```