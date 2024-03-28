# McDiscordWebhook

McDiscordWebhook lets you integrate discord into your fabric server without the need of any extra bots.

![GitHub License](https://img.shields.io/github/license/yo56789/McDiscordWebhook)
![GitHub repo size](https://img.shields.io/github/repo-size/yo56789/McDiscordWebhook)
![Static Badge](https://img.shields.io/badge/download-Modrinth-green)

## Quick Start
Install the mod, and run it for the first time. The mod will generate a `config/mcdiscwbhk.properties` file with mod configuration in it.
Modify these properties to suit your needs.

## Config Documentation
### `webhook-uri`
**Data type:** String (URL)
<br>
**Description:** The URL for the webhook, copied from the Discord channel settings.
<br>
**Default Value:** (empty)

### `server-webhook-name`
**Data type:** String
<br>
**Description:** The username displayed for all non-player related activities.
<br>
**Default Value:** Server

### `user-avatar-url`
**Data type:** String (URL)
<br>
**Description:** The URL for accessing player heads, with the player's UUID replaced with %s
<br>
**Default Value:** https://mc-heads.net/avatar/%s

### `webhook-mode`
**Data type:** String [message, embed]
<br>
**Description:** Setting to select embed mode or message mode
<br>
**Default Value:** message

### Events
All settings prefixed with `event-` are messages that get sent when an event is triggered. More information for each event is shown in the config file.

## Build
Run these commands in order:
```bash
git clone https://github.com/yo56789/McDiscordWebhook.git
cd McDiscordWebhook
./gradlew genSources
./gradlew build
```
The completed mod file should be available in `target/`


