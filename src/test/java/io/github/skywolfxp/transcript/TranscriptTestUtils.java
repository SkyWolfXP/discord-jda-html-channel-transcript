package io.github.skywolfxp.transcript;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.*;
import net.dv8tion.jda.api.interactions.InteractionType;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class TranscriptTestUtils {
  private final static String AVATAR_URL_1 =
    "https://cdn.discordapp.com/avatars/545902760453996546/8d3a7164a3ed3e0f1a500e776fa07963.png?";
  private final static String AVATAR_URL_2 =
    "https://cdn.discordapp.com/avatars/1093684128437764136/812a2439d19dabf4da5e6f211b3eeb88.png?";
  private final static String RICH_CUSTOM_EMOJI = "https://cdn.discordapp.com/emojis/1353135081476329523.webp?";

  @NotNull
  public static Guild mockGuild() {
    Guild guild = mock(Guild.class);
    when(guild.getName()).thenReturn("test-guild");

    return guild;
  }

  @NotNull
  public static TextChannel mockTextChannel() {
    TextChannel textChannel = mock(TextChannel.class);
    when(textChannel.getName()).thenReturn("test-channel");

    Guild guild = mockGuild();
    when(textChannel.getGuild()).thenReturn(guild);

    return textChannel;
  }

  @NotNull
  public static User mockAuthor(String userId, String name, String avatarUrl, boolean isBot) {
    User user = mock(User.class);
    when(user.getId()).thenReturn(userId);
    when(user.getEffectiveName()).thenReturn(name);
    when(user.getEffectiveAvatarUrl()).thenReturn(avatarUrl);
    when(user.isBot()).thenReturn(isBot);

    return user;
  }

  @NotNull
  public static Message.Attachment mockAttachment(boolean isImage) {
    Message.Attachment attachment = mock(Message.Attachment.class);
    when(attachment.isImage()).thenReturn(isImage);
    when(attachment.getUrl()).thenReturn(AVATAR_URL_1);
    when(attachment.getSize()).thenReturn(420);
    when(attachment.getFileName()).thenReturn(isImage ? "image.png" : "file.txt");

    return attachment;
  }

  @NotNull
  public static MessageReaction mockReactionUnicodeEmoji() {
    EmojiUnion emojiUnion = mock(EmojiUnion.class);
    UnicodeEmoji unicodeEmoji = mock(UnicodeEmoji.class);
    MessageReaction reaction = mock(MessageReaction.class);

    when(reaction.getCount()).thenReturn(69);
    when(reaction.getEmoji()).thenReturn(emojiUnion);
    when(reaction.getEmoji().getType()).thenReturn(Emoji.Type.UNICODE);
    when(reaction.getEmoji().asUnicode()).thenReturn(unicodeEmoji);
    when(reaction.getEmoji().asUnicode().getAsCodepoints()).thenReturn("U+1F49A");

    return reaction;
  }

  @NotNull
  public static MessageReaction mockReactionCustomEmoji() {
    EmojiUnion emojiUnion = mock(EmojiUnion.class);
    CustomEmoji customEmoji = mock(CustomEmoji.class);
    MessageReaction reaction = mock(MessageReaction.class);

    when(reaction.getCount()).thenReturn(69);
    when(reaction.getEmoji()).thenReturn(emojiUnion);
    when(reaction.getEmoji().getType()).thenReturn(Emoji.Type.CUSTOM);
    when(reaction.getEmoji().asCustom()).thenReturn(customEmoji);
    when(reaction.getEmoji().asCustom().getImageUrl()).thenReturn(AVATAR_URL_2);

    return reaction;
  }

  @NotNull
  public static MessageReaction mockReactionRichCustomEmoji() {
    EmojiUnion emojiUnion = mock(EmojiUnion.class);
    RichCustomEmoji richCustomEmoji = mock(RichCustomEmoji.class);
    MessageReaction reaction = mock(MessageReaction.class);

    when(reaction.getCount()).thenReturn(69);
    when(reaction.getEmoji()).thenReturn(emojiUnion);
    when(reaction.getEmoji().getType()).thenReturn(Emoji.Type.CUSTOM);
    when(reaction.getEmoji().asCustom()).thenReturn(richCustomEmoji);
    when(reaction.getEmoji().asCustom().getImageUrl()).thenReturn(RICH_CUSTOM_EMOJI);

    return reaction;
  }

  @NotNull
  @SuppressWarnings("deprecation")
  public static Message.Interaction mockInteraction(@NotNull User user) {
    Message.Interaction interaction = mock(Message.Interaction.class);
    when(interaction.getUser()).thenReturn(user);
    when(interaction.getType()).thenReturn(InteractionType.COMMAND);
    when(interaction.getName()).thenReturn("command");

    return interaction;
  }

  @NotNull
  public static String createMessageContentRaw() {
    return """
           **Bold**
           *Italic*
           ***Bold Italic***
           
           `Code Inline`
           
           ```
           Code Block
           ```
           """;
  }

  @NotNull
  public static MessageEmbed createMessageEmbed() {
    return new EmbedBuilder()
      .setAuthor("Author", AVATAR_URL_1)
      .setTitle("Title")
      .setDescription("Description")
      .addField("Field Name #1", "Field Value #1", false)
      .addField("Field Name #2", "Field Value #2", false)
      .setImage(AVATAR_URL_1)
      .setThumbnail(AVATAR_URL_1)
      .setFooter("Footer", AVATAR_URL_1)
      .setTimestamp(Instant.now())
      .setColor(Color.GREEN)
      .build();
  }

  @NotNull
  public static List<ActionRow> createActionRows() {
    List<Button> actionRowButtons = new ArrayList<>();
    actionRowButtons.add(Button.of(ButtonStyle.PRIMARY, "1", "Primary", Emoji.fromUnicode("💠")));
    actionRowButtons.add(Button.of(ButtonStyle.SECONDARY, "2", "Secondary", Emoji.fromUnicode("💠")));
    actionRowButtons.add(Button.of(ButtonStyle.SUCCESS, "3", "Success", Emoji.fromUnicode("💠")));
    actionRowButtons.add(Button.of(ButtonStyle.DANGER, "4", "Danger", Emoji.fromUnicode("💠")));
    actionRowButtons.add(Button.of(ButtonStyle.LINK, "https://github.com/SkyWolfXP", "Link", Emoji.fromUnicode("🔗")));

    List<ActionRow> actionRows = new ArrayList<>();
    actionRows.add(ActionRow.of(actionRowButtons));
    actionRows.add(ActionRow.of(actionRowButtons.stream().map(Button::asDisabled).toList()));

    return actionRows;
  }
}