linear_extrude(height = 309.0, twist = 0.0, scale = 1.0, slices = 1, center = true)
{
    scale([97.0, 97.0])
    {
        M52();
    }
}

module M52()
{
    polygon
    (
        points =
        [
            [-0.5, -0.5], 
            [0.5, -0.5], 
            [0.5, 0.5], 
            [-0.5, 0.5]
        ],
        paths =
        [
            [0, 1, 2, 3]
        ]
    );
}
