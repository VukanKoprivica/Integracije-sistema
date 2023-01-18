<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="text" media-type="text/plain" encoding="UTF-8" />
	<xsl:strip-space elements="*" />
	<xsl:variable name="newline"><xsl:text>
</xsl:text></xsl:variable>

	<xsl:template match="title">
		<xsl:value-of select="$newline" />
		<xsl:value-of select="." />
		<xsl:text> (</xsl:text>
		<xsl:value-of select="../@year" />
		<xsl:text>)</xsl:text>
		<xsl:value-of select="$newline" />
	</xsl:template>

	<xsl:template match="genre">
		<xsl:text>Genre: </xsl:text>
		<xsl:value-of select="." />
		<xsl:value-of select="$newline" />
	</xsl:template>

	<xsl:template match="actor[1]">
		<xsl:text>Cast: </xsl:text>
		<xsl:value-of select="." />
	</xsl:template>

	<xsl:template match="actor">
		<xsl:text>, </xsl:text>
		<xsl:value-of select="." />
	</xsl:template>

	<xsl:template match="actor[last()]">
		<xsl:text>, </xsl:text>
		<xsl:value-of select="." />
		<xsl:value-of select="$newline" />
	</xsl:template>

	<xsl:template match="plot">
		<xsl:text>Plot: </xsl:text>
		<xsl:value-of select="." />
		<xsl:value-of select="$newline" />
	</xsl:template>

	<xsl:template match="movie">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="movies">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>

</xsl:stylesheet>
