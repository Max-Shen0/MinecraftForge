package cpw.mods.fml.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public class FMLProxyPacket extends Packet {
    final String channel;
    private final ByteBuf payload;
    private INetHandler netHandler;

    private FMLProxyPacket(byte[] payload, String channel)
    {
        this(Unpooled.wrappedBuffer(payload), channel);
    }

    public FMLProxyPacket(S3FPacketCustomPayload original)
    {
        this(original.func_149168_d(), original.func_149169_c());
    }

    public FMLProxyPacket(C17PacketCustomPayload original)
    {
        this(original.func_149558_e(), original.func_149559_c());
    }

    public FMLProxyPacket(ByteBuf payload, String channel)
    {
        this.channel = channel;
        this.payload = payload;
    }
    @Override
    public void func_148837_a(PacketBuffer packetbuffer) throws IOException
    {
        // NOOP - we are not built this way
    }

    @Override
    public void func_148840_b(PacketBuffer packetbuffer) throws IOException
    {
        // NOOP - we are not built this way
    }

    @Override
    public void func_148833_a(INetHandler inethandler)
    {
        this.netHandler = inethandler;
        EmbeddedChannel internalChannel = NetworkRegistry.INSTANCE.getChannel(this.channel);
        if (internalChannel != null)
        {
            internalChannel.writeInbound(this);
        }
    }

    public ByteBuf payload()
    {
        return payload;
    }

    public Packet toC17Packet()
    {
        return new C17PacketCustomPayload(channel, payload.array());
    }

    public Packet toS3FPacket()
    {
        return new S3FPacketCustomPayload(channel, payload.array());
    }
}
