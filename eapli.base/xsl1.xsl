<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <htlm>
            <body>
                <h1>Factory Floor</h1>
                <h2>Products</h2>
                <table border="1">
                    <tr>
                        <th>Factory Code</th>
                        <th>Comercial Code</th>
                        <th>Unit</th>
                        <th>Category</th>
                        <th>Brief Description</th>
                        <th>Complete Description</th>
                    </tr>
                    <xsl:for-each select="FactoryFloor/products/product">
                        <tr>
                            <td>
                                <xsl:value-of select="codeFactory/@name"/>
                            </td>
                            <td>
                                <xsl:value-of select="codeComercial"/>
                            </td>
                            <td>
                                <xsl:value-of select="unit"/>
                            </td>
                            <td>
                                <xsl:value-of select="category"/>
                            </td>
                            <td>
                                <xsl:value-of select="descBrief"/>
                            </td>
                            <td>
                                <xsl:value-of select="descComplete"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Raw Materials</h2>
                <table border="1">
                    <tr>
                        <th>Id</th>
                        <th>Description</th>
                        <th>Category</th>
                    </tr>
                    <xsl:for-each select="FactoryFloor/rawMaterials/rawMaterial">
                        <tr>
                            <td>
                                <xsl:value-of select="internalCode/@name"/>
                            </td>
                            <td>
                                <xsl:value-of select="description"/>
                            </td>
                            <td>
                                <xsl:value-of select="category/description"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Raw Material Categories</h2>
                <table border="1">
                    <tr>
                        <th>Description</th>
                        <th>Code</th>
                    </tr>
                    <xsl:for-each select="rawMaterialCategory">
                        <tr>
                            <td>
                                <xsl:value-of select="description"/>
                            </td>
                            <td>
                                <xsl:value-of select="code/@name"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Deposits</h2>
                <table border="1">
                    <tr>
                        <th>Code</th>
                        <th>Description</th>
                        <th>ID</th>
                    </tr>
                    <xsl:for-each select="FactoryFloor/deposits/deposit">
                        <tr>
                            <td>
                                <xsl:value-of select="cod/@name"/>
                            </td>

                            <td>
                                <xsl:value-of select="Descricao"/>
                            </td>
                            <td>
                                <xsl:value-of select="@id"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Production Lines</h2>
                <table border="1">
                    <tr>
                        <th>Name</th>
                    </tr>
                    <xsl:for-each select="FactoryFloor/productionLines/productionLine">
                        <tr>
                            <td>
                                <xsl:value-of select="productionLineId/@name"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Production Orders</h2>
                <table border="1">
                    <tr>
                        <th>Order Code</th>
                        <th>Status</th>
                        <th>Execution started at</th>
                        <th>Unity</th>
                        <th>Quantity</th>
                        <th>Expected Date</th>
                        <th>Product ID</th>
                    </tr>
                </table>
                <xsl:for-each select="FactoryFloor/productionOrders/productionOrder">
                    <tr>
                        <td>
                            <xsl:value-of select="codeOrder/@name"/>
                        </td>
                        <td>
                            <xsl:value-of select="status"/>
                        </td>
                        <td>
                            <xsl:value-of select="startExecution"/>
                        </td>
                        <td>
                            <xsl:value-of select="unity"/>
                        </td>
                        <td>
                            <xsl:value-of select="quantity"/>
                        </td>
                        <td>
                            <xsl:value-of select="expectedDate"/>
                        </td>
                        <td>
                            <xsl:value-of select="productId/descComplete"/>
                        </td>
                    </tr>
                </xsl:for-each>


                <h2>Machines</h2>
                <table border="1">
                    <tr>
                        <th>Internal Code</th>
                        <th>Series Number</th>
                        <th>Description</th>
                        <th>Brand</th>
                        <th>Model</th>
                        <th>Production Line</th>
                    </tr>
                </table>
                <xsl:for-each select="machines">
                    <tr>
                        <td>
                            <xsl:value-of select="internalCode/@name"/>
                        </td>
                        <td>
                            <xsl:value-of select="seriesNumber"/>
                        </td>
                        <td>
                            <xsl:value-of select="description"/>
                        </td>
                        <td>
                            <xsl:value-of select="brand"/>
                        </td>
                        <td>
                            <xsl:value-of select="model"/>
                        </td>
                        <td>
                            <xsl:value-of select="productionLine/productionLineId/@name"/>
                        </td>
                    </tr>
                </xsl:for-each>

            </body>
        </htlm>
    </xsl:template>


</xsl:stylesheet>