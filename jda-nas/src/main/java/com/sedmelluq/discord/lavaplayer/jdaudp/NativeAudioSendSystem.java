package com.sedmelluq.discord.lavaplayer.jdaudp;

import com.sedmelluq.discord.lavaplayer.udpqueue.natives.UdpQueueManager;
import net.dv8tion.jda.api.audio.factory.IAudioSendSystem;
import net.dv8tion.jda.api.audio.factory.IPacketProvider;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class NativeAudioSendSystem implements IAudioSendSystem {
  private final long queueKey;
  private final NativeAudioSendFactory audioSendSystem;
  private final IPacketProvider packetProvider;

  public NativeAudioSendSystem(long queueKey, NativeAudioSendFactory audioSendSystem, IPacketProvider packetProvider) {
    this.queueKey = queueKey;
    this.audioSendSystem = audioSendSystem;
    this.packetProvider = packetProvider;
  }

  @Override
  public void start() {
    audioSendSystem.addInstance(this);
  }

  @Override
  public void shutdown() {
    audioSendSystem.removeInstance(this);
  }

  void populateQueue(UdpQueueManager queueManager) {
    int remaining = queueManager.getRemainingCapacity(queueKey);
    boolean emptyQueue = queueManager.getCapacity() - remaining > 0;

    for (int i = 0; i < remaining; i++) {
      ByteBuffer packet = packetProvider.getNextPacketRaw(emptyQueue);
      InetSocketAddress address = packetProvider.getSocketAddress();

      if (packet == null || !queueManager.queuePacket(queueKey, packet, address)) {
        break;
      }
    }
  }
}
