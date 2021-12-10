<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" indent="no"/>


    <xsl:template match="/">
        <xsl:apply-templates select="FactoryFloor/products/product"/>

        <xsl:apply-templates select="FactoryFloor/rawMaterials/rawMaterial"/>

        <xsl:apply-templates select="FactoryFloor/rawMaterialCategories/rawMaterialCategory"/>

        <xsl:apply-templates select="FactoryFloor/deposits/deposit"/>

        <xsl:apply-templates select="FactoryFloor/productionLines/productionLine"/>

        <xsl:apply-templates select="FactoryFloor/productionOrders/productionOrder"/>

        <xsl:apply-templates select="FactoryFloor/machines/machines"/>


    </xsl:template>

    <xsl:template match="product">
        <xsl:value-of select="concat('product - ',codeFactory/@name, ', ', codeComercial,',' ,unit, ',',category, ',',descBrief,',', descComplete, '&#xa;')"/>
    </xsl:template>

    <xsl:template match="rawMaterial">
        <xsl:value-of select="concat(' raw material - ',internalCode/@name,',', description, ',', category/description, '&#xa;')"/>
    </xsl:template>

    <xsl:template match="rawMaterialCategory">
        <xsl:value-of select="concat('raw material category - ',code/@name, ', ', description, '&#xa;')"/>
    </xsl:template>

    <xsl:template match="deposit">
        <xsl:value-of select="concat('deposit - ',cod/@name, ',', description,',', @id, '&#xa;')"/>
    </xsl:template>

    <xsl:template match="productionLine">
        <xsl:value-of select="concat('production line - ',productionLineId/@name,  '&#xa;')"/>
    </xsl:template>

    <xsl:template match="productionOrder">
        <xsl:value-of select="concat('production order - ',codeOrder/@name, ', ', status/status, ', ', startExecution, ', ', unity, ', ', quantity,', ', expectedDate, ', ', productId/descComplete,  '&#xa;')"/>
    </xsl:template>

    <xsl:template match="machines">
        <xsl:value-of select="concat('machine - ',internalCode/@name, ', ', seriesNumber,',', description,',',brand,',',model,',',productionLine/productionLineId/@name, '&#xa;')"/>
    </xsl:template>

</xsl:stylesheet>


