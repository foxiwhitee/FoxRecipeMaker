package foxiwhitee.FoxRecipeMaker.network;

import foxiwhitee.FoxRecipeMaker.network.packets.*;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public enum NetworkPackets {
    VANILLA_CRAFT_PACKET(VanillaCraftPacket.class),
    CUSTOM_CRAFT_PACKET(CustomCraftPacket.class);

    private final Class<? extends BasePacket> pktClass;
    private final Constructor<? extends BasePacket> pktConstructor;
    private static final Map<Class<? extends BasePacket>, NetworkPackets> CLASS_LOOKUP = new HashMap<>();

    NetworkPackets(Class<? extends BasePacket> packetClass) {
        this.pktClass = packetClass;
        Constructor<? extends BasePacket> constructor = null;

        try {
            constructor = packetClass.getConstructor(ByteBuf.class);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new IllegalStateException("Packet class " + packetClass.getName() + " must have a ByteBuf constructor", e);
        }

        this.pktConstructor = constructor;
    }

    static {
        for (NetworkPackets packet : values()) {
            CLASS_LOOKUP.put(packet.pktClass, packet);
        }
    }

    public static NetworkPackets fromId(int packetId) {
        return values()[packetId];
    }

    public static NetworkPackets fromClass(Class<? extends BasePacket> packetClass) {
        return CLASS_LOOKUP.get(packetClass);
    }

    public BasePacket createInstance(ByteBuf buffer) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return pktConstructor.newInstance(buffer);
    }
}