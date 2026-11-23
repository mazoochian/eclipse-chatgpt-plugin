package com.github.gradusnikov.eclipse.assistai.mcp.local;

import java.time.Duration;

import com.github.gradusnikov.eclipse.assistai.Activator;
import com.github.gradusnikov.eclipse.assistai.preferences.PreferenceConstants;

/**
 * How long the chat's MCP clients wait for a single request, tool calls included.
 * <p>
 * The MCP SDK's own default is 20 seconds. That is shorter than a test run or a build
 * routinely takes, and shorter than the 60 seconds a long execution tool waits before
 * handing back an operation id - so the chat abandoned those calls with a timeout while
 * the tool was still working. The value is a preference, read when a client is built;
 * changing it restarts the clients.
 */
public final class McpClientTimeouts
{
    /** Bounds for the preference, also used by its editor. */
    public static final int MIN_SECONDS = 10;
    public static final int MAX_SECONDS = 3600;

    private McpClientTimeouts()
    {
    }

    public static Duration toolCallTimeout()
    {
        int seconds = PreferenceConstants.ASSISTAI_TOOL_CALL_TIMEOUT_SECONDS_DEFAULT;
        Activator activator = Activator.getDefault();
        if ( activator != null )
        {
            int stored = activator.getPreferenceStore().getInt( PreferenceConstants.ASSISTAI_TOOL_CALL_TIMEOUT_SECONDS );
            if ( stored > 0 )
            {
                seconds = stored;
            }
        }
        return Duration.ofSeconds( Math.clamp( seconds, MIN_SECONDS, MAX_SECONDS ) );
    }
}
