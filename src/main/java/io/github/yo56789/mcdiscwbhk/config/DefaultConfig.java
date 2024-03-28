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
            # Type "message" or "embed"
            # "message" only sends raw text messages. "embed" sends content in embeds
            webhook-mode=message
                        
            # Events
            # Leaving blank will cause event to be disabled
                        
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
