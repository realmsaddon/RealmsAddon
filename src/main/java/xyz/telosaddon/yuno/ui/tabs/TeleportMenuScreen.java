package xyz.telosaddon.yuno.ui.tabs;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import xyz.telosaddon.yuno.utils.LocalAPI;

import static xyz.telosaddon.yuno.TelosAddon.CONFIG;

public class TeleportMenuScreen extends BaseOwoScreen<FlowLayout> {
    final String[] NAServerNames = {
            "Ashburn", "Bayou", "Cedar", "Dakota",
            "Eagleton", "Farrion", "Groveridge", "Holloway",
            "Hub-1", "Hub-2", "Hub-3"
    };
    final String[] EUServerNames = {
            "Astra", "Balkan", "Creska", "Draskov",
            "Estenmoor", "Falkenburg", "Galla", "Helmburg",
            "Ivarn", "Jarnwald", "Krausenfeld", "Lindenburg",
            "Hub-1", "Hub-2", "Hub-3"
    };
    final String[] SGServerNames = {
            "Asura", "Bayan", "Chantara", "Hub-1", 
            "Hub-2", "Hub-3"
    };

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout rootComponent) {
        rootComponent
                .surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER)
                .padding(Insets.of(5));

        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                        .child(Components.label(Text.literal("Teleport Menu")))
                        .child(
                                Containers.grid(Sizing.content(), Sizing.content(), 3, calculateRows()) // Dynamically calculate the number of rows
                                        .<GridLayout>configure(layout -> {
                                            String[] finalServerNames = getServerName();
                                            layout.allowOverflow();
                                            for (int i = 0; i < calculateRows(); i++) { // Iterate over the number of rows
                                                for (int j = 0; j < 3; j++) { // 3 columns
                                                    int finalI = i;
                                                    int finalJ = j;

                                                    int index = finalJ * calculateRows() + finalI; // Dynamic index calculation

                                                    if (index < finalServerNames.length) { // Prevent index errors
                                                        layout.child(Components.button(Text.literal(finalServerNames[index]), button -> {
                                                            if (client == null || client.player == null) return;
                                                            client.player.networkHandler.sendChatCommand("joinq " + finalServerNames[index]);
                                                        }).renderer(ButtonComponent.Renderer.flat(
                                                                new java.awt.Color(0, 0, 0, 150).getRGB(),
                                                                CONFIG.fillColor(),
                                                                new java.awt.Color(0, 0, 0, 50).getRGB()))
                                                                .sizing(Sizing.fixed(55), Sizing.fixed(20)).margins(Insets.of(5)), j, i
                                                        );
                                                    }
                                                }
                                            }
                                        })
                                        .surface(Surface.DARK_PANEL)
                                        .verticalAlignment(VerticalAlignment.CENTER)
                                        .horizontalAlignment(HorizontalAlignment.CENTER)
                                        .margins(Insets.of(5))
                        )
                        .margins(Insets.of(30))
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
        );
    }

    private String[] getServerName() {
        String[] serverNames;
        String currentArea = LocalAPI.getCurrentCharacterWorld();
        System.out.println(currentArea);
        if (currentArea.trim().charAt(0) == 'N') {
            serverNames = NAServerNames;
        } else if (currentArea.trim().charAt(0) == 'G') {
            serverNames = EUServerNames;
        } else {
            serverNames = SGServerNames;
        }
        return serverNames;
    }

    // Dynamically calculate the number of rows based on the number of servers
    private int calculateRows() {
        String[] serverNames = getServerName();
        int totalServers = serverNames.length;
        return (int) Math.ceil(totalServers / 3.0); // 3 columns
    }
}
