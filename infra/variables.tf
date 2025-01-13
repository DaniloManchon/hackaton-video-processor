variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

variable "cluster_name" {
  description = "Name of the EKS Cluster"
  type        = string
  default     = "hackaton-cluster"
}

variable "app_name" {
  description = "Name of the App"
  type        = string
  default     = "video-processor-api"
}