package io.github.yo56789.mcdiscwbhk.config;

public class DefaultConfig {
    public static final String DEFAULTCONFIG = """
            # Discord webhook URI
            # How to get a webhook: https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks
            webhook-uri=https://discord.com/api/webhooks/1221539658421244157/MHFQu9KUiV7xpiXIMIqo8_hs6eLNdMCtMVx92w3XavozhDLUqbD4TaT-e16hcbweACx9
                        
            # Server name
            # Can be set to anything, and shows for server-related events
            server-webhook-name=Server
                        
            # User avatar provider
            # Replace the part where the UUID is located with %s
            user-avatar-url=https://mc-heads.net/avatar/%s
                        
            # Webhook mode
            # Type "message" or "embed"
            # "message" only sends raw text messages. "embed" sends content in embeds
            webhook-mode=embed
                        
            # Events (Leave blank to ignore event)
                        
            # Server starting
            event-server-starting=Server Starting!
                        
            # Server started
            event-server-started=Server Started!
                        
            # Server stopping
            event-server-stopping=Server Stopping!
                        
            # Server stopped
            event-server-stopped=Server Stopped!
                        
            # Player join
            event-player-join=%s joined!
                        
            # Player leave
            event-player-leave=%s left!
            """;
}
