package foxiwhitee.FoxRecipeMaker.network.packets;

import foxiwhitee.FoxRecipeMaker.network.BasePacket;
import foxiwhitee.FoxRecipeMaker.network.IInfoPacket;
import foxiwhitee.FoxRecipeMaker.tiles.TileCustomReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileVanillaReciper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomCraftPacket extends BasePacket {
    private final int worldId, x, y, z;
    private final Map<Integer, Boolean> checkBoxesData;
    private final Map<Integer, Integer> slidersData;

    public CustomCraftPacket(ByteBuf buffer) throws IOException {
        super(buffer);

        this.worldId = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();

        this.checkBoxesData = new HashMap<>();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            checkBoxesData.put(buffer.readInt(), buffer.readBoolean());
        }

        this.slidersData = new HashMap<>();
        size = buffer.readInt();
        for (int i = 0; i < size; i++) {
            slidersData.put(buffer.readInt(), buffer.readInt());
        }
        int a =0;
    }

    public CustomCraftPacket(int worldId, int x, int y, int z, Map<Integer, Boolean> checkBoxesData, Map<Integer, Integer> slidersData) throws IOException {
        super();
        this.worldId = worldId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.checkBoxesData = checkBoxesData;
        this.slidersData = slidersData;

        ByteBuf data = Unpooled.buffer();
        data.writeInt(getId());

        data.writeInt(worldId);
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);

        data.writeInt(checkBoxesData.size());
        checkBoxesData.forEach((integer, aBoolean) -> {
            data.writeInt(integer);
            data.writeBoolean(aBoolean);
        });

        data.writeInt(slidersData.size());
        slidersData.forEach((integer, integer1) -> {
            data.writeInt(integer);
            data.writeInt(integer1);
        });

        setPacketData(data);
    }

    @Override
    public void handleServerSide(IInfoPacket network, BasePacket packet, EntityPlayer player) {
        Container container = player.openContainer;
        WorldServer worldServer = DimensionManager.getWorld(worldId);
        TileEntity te = worldServer.getTileEntity(x, y, z);
        if (te instanceof TileCustomReciper) {
            ((TileCustomReciper) te).outputRecipeToFile(checkBoxesData, slidersData);
        }
    }

}