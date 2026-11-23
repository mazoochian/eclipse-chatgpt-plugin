package com.github.gradusnikov.eclipse.assistai.mcp.local;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.github.gradusnikov.eclipse.assistai.Activator;
import com.github.gradusnikov.eclipse.assistai.preferences.PreferenceConstants;

public class McpClientTimeoutsPDETest
{
    private final IPreferenceStore store = Activator.getDefault().getPreferenceStore();

    @AfterEach
    public void restoreDefault()
    {
        store.setToDefault( PreferenceConstants.ASSISTAI_TOOL_CALL_TIMEOUT_SECONDS );
    }

    @Test
    public void defaultsToFiveMinutesNotTheSdksTwentySeconds()
    {
        store.setToDefault( PreferenceConstants.ASSISTAI_TOOL_CALL_TIMEOUT_SECONDS );

        assertEquals( Duration.ofSeconds( 300 ), McpClientTimeouts.toolCallTimeout() );
    }

    @Test
    public void usesTheStoredValue()
    {
        store.setValue( PreferenceConstants.ASSISTAI_TOOL_CALL_TIMEOUT_SECONDS, 900 );

        assertEquals( Duration.ofSeconds( 900 ), McpClientTimeouts.toolCallTimeout() );
    }

    @Test
    public void keepsAStoredValueWithinBounds()
    {
        store.setValue( PreferenceConstants.ASSISTAI_TOOL_CALL_TIMEOUT_SECONDS, 1 );
        assertEquals( Duration.ofSeconds( McpClientTimeouts.MIN_SECONDS ), McpClientTimeouts.toolCallTimeout() );

        store.setValue( PreferenceConstants.ASSISTAI_TOOL_CALL_TIMEOUT_SECONDS, 1_000_000 );
        assertEquals( Duration.ofSeconds( McpClientTimeouts.MAX_SECONDS ), McpClientTimeouts.toolCallTimeout() );
    }
}
