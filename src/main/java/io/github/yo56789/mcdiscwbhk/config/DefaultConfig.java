package io.github.yo56789.mcdiscwbhk.config;

public class DefaultConfig {
    public static final String DEFAULTCONFIG = """
            # Discord webhook URI
            # How to get a webhook: https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks
            webhook-uri=
                        
            # Server name
            # Can be set to anything, and shows for server-related events
            server-webhook-name=Server
                        
            # User avatar provider
            # Replace the part where the UUID is located with %s
            user-avatar-url=https://mc-heads.net/avatar/%s
                        
            # Webhook mode
            # Options: "message", "embed" or "list"
            # "message" sends messages with the bot using the persons mc username. "embed" sends everything in embeds. "list" sends messages like a list.
            webhook-mode=message
            
            # Format for sending messages in "list" mode
            # First %s is username Second %s is message content
            # Default: %s > %s
            list-message-format=**%s** > %s
                        
            # Events
            # Leaving blank will cause event to be disabled
                        
            # Player Message
            event-player-message-enabled=true
                        
            # Server starting
            event-server-starting-enabled=false
            event-server-starting-message=Server Starting!
                        
            # Server started
            event-server-started-enabled=true
            event-server-started-message=Server Started!
                        
            # Server stopping
            event-server-stopping-enabled=false
            event-server-stopping-message=Server Stopping!
                        
            # Server stopped
            event-server-stopped-enabled=true
            event-server-stopped-message=Server Stopped!
                        
            # Player join
            # %s = username of player
            event-player-join-enabled=true
            event-player-join-message=%s joined!
                        
            # Player leave
            # %s = username of player
            event-player-leave-enabled=true
            event-player-leave-message=%s left!""";

}
