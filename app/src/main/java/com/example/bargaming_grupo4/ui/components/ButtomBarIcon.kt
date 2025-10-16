package com.example.bargaming_grupo4.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.bargaming_grupo4.ui.theme.PinkButton
import com.example.bargaming_grupo4.ui.theme.WhiteVariant

@Composable
fun BarGamingNavButton(
    icon: ImageVector,
    contentDescription: String?,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isSelected) PinkButton else WhiteVariant,
            modifier = Modifier.size(32.dp)
        )
    }
}
