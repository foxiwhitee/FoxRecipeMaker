package foxiwhitee.FoxRecipeMaker.network.packets;

import foxiwhitee.FoxRecipeMaker.network.BasePacket;
import foxiwhitee.FoxRecipeMaker.network.IInfoPacket;
import foxiwhitee.FoxRecipeMaker.tiles.TileReciper;
import foxiwhitee.FoxRecipeMaker.tiles.TileVanillaReciper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.io.*;

public class VanillaCraftPacket extends BasePacket {
    private final int worldId, x, y, z;
    private final boolean shaped, mirror, ore, tag;

    public VanillaCraftPacket(ByteBuf buffer) throws IOException {
        super(buffer);

        this.worldId = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();

        this.shaped = buffer.readBoolean();
        this.mirror = buffer.readBoolean();
        this.ore = buffer.readBoolean();
        this.tag = buffer.readBoolean();
    }

    public VanillaCraftPacket(int worldId, int x, int y, int z, boolean shaped, boolean mirror, boolean ore, boolean tag) throws IOException {
        super();
        this.worldId = worldId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.shaped = shaped;
        this.mirror = mirror;
        this.ore = ore;
        this.tag = tag;

        ByteBuf data = Unpooled.buffer();
        data.writeInt(getId());

        data.writeInt(worldId);
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);

        data.writeBoolean(shaped);
        data.writeBoolean(mirror);
        data.writeBoolean(ore);
        data.writeBoolean(tag);

        setPacketData(data);
    }

    @Override
    public void handleServerSide(IInfoPacket network, BasePacket packet, EntityPlayer player) {
        Container container = player.openContainer;
        WorldServer worldServer = DimensionManager.getWorld(worldId);
        TileEntity te = worldServer.getTileEntity(x, y, z);
        if (te instanceof TileVanillaReciper) {
            ((TileVanillaReciper) te).outputRecipeToFile(shaped, mirror, ore, tag);
        }
    }

}